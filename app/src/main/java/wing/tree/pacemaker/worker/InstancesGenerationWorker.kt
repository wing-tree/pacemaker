package wing.tree.pacemaker.worker

import android.content.Context
import android.icu.util.Calendar
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import wing.tree.pacemaker.data.extension.hour
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.millisecond
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.second
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.entity.Reminder
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.entity.Time
import wing.tree.pacemaker.domain.extension.long
import wing.tree.pacemaker.domain.usecase.AddAllInstanceUseCase
import wing.tree.pacemaker.domain.usecase.LoadRoutinesUseCase
import wing.tree.pacemaker.domain.usecase.core.Result.Complete
import wing.tree.pacemaker.scheduler.ReminderScheduler

@HiltWorker
class InstancesGenerationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addAllInstanceUseCase: AddAllInstanceUseCase,
    private val loadRoutinesUseCase: LoadRoutinesUseCase,
    private val reminderScheduler: ReminderScheduler,
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
                    .filter {
                        dueDate.julianDay in it.startDay..it.endDay
                    }.map {
                        object : Instance {
                            override val id: Long = ZERO.long
                            override val routineId: Long = it.id
                            override val title: String = it.title
                            override val description: String = it.description
                            override val begin: Time = it.begin
                            override val end: Time = it.end
                            override val day: Int = dueDate.julianDay
                            override val status: Status = Status.Todo
                            override val reminder: Reminder = it.reminder
                        }
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
