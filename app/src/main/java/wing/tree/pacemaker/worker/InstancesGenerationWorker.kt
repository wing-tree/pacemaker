package wing.tree.pacemaker.worker

import android.content.Context
import android.icu.util.Calendar
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.millisecond
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.second
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.usecase.AddAllInstanceUseCase
import wing.tree.pacemaker.domain.usecase.LoadRoutinesUseCase
import wing.tree.pacemaker.domain.usecase.core.Result.Complete
import wing.tree.pacemaker.mapper.RoutineMapper
import wing.tree.pacemaker.model.Instance
import wing.tree.pacemaker.scheduler.ReminderScheduler

@HiltWorker
class InstancesGenerationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addAllInstanceUseCase: AddAllInstanceUseCase,
    private val loadRoutinesUseCase: LoadRoutinesUseCase,
    private val reminderScheduler: ReminderScheduler,
    private val routineMapper: RoutineMapper,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        when (val result = loadRoutinesUseCase().firstOrNull()) {
            is Complete.Success -> {
                val now = Calendar.getInstance()
                val dueDate = Calendar.getInstance().apply {
                    hourOfDay = ZERO
                    minute = ZERO
                    second = ZERO
                    millisecond = ZERO
                }

                if (dueDate.before(now)) {
                    dueDate.add(Calendar.HOUR_OF_DAY, 24)
                }

                // TODO 설정된 요일도 반영, 위 캘린더 이용, 오늘 무슨 요일인지에 따라, 예약 여부 판단하면 될 듯.
                val instances = result
                    .data
                    .map { routineMapper.toModel(it) }
                    .filter {
                        dueDate.julianDay in it.startDay..it.endDay
                    }.map {
                        Instance(
                            routineId = it.id,
                            title = it.title,
                            description = it.description,
                            begin = it.begin,
                            end = it.end,
                            day = dueDate.julianDay,
                            status = Status.Todo,
                            reminder = it.reminder
                        )
                    }

                if (addAllInstanceUseCase(instances).isSuccess) {
                    instances.forEach { instance ->
                        reminderScheduler.scheduleReminder(instance)
                    }
                }
            }

            else -> return Result.failure()
        }

        return Result.success()
    }
}
