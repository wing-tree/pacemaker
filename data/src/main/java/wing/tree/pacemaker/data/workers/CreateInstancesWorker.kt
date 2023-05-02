package wing.tree.pacemaker.data.workers

import android.content.Context
import android.icu.util.Calendar
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.models.Instance
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.entities.Status
import wing.tree.pacemaker.domain.use.cases.AddAllInstanceUseCase
import wing.tree.pacemaker.domain.use.cases.LoadRoutinesUseCase
import wing.tree.pacemaker.domain.use.cases.core.Result.Complete

@HiltWorker
class CreateInstancesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addAllInstanceUseCase: AddAllInstanceUseCase,
    private val loadRoutinesUseCase: LoadRoutinesUseCase,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        when (val result = loadRoutinesUseCase().firstOrNull()) {
            is Complete.Success -> {
                val calendar = Calendar.getInstance().apply {
                    add(Calendar.HOUR_OF_DAY, ONE)
                }
                // TODO 설정된 요일도 반영, 위 캘린더 이용, 오늘 무슨 요일인지에 따라, 예약 여부 판단하면 될 듯.
                val instances = result
                    .data
                    .filter {
                        calendar.julianDay in it.startDay..it.endDay
                    }.map {
                        Instance(
                            routineId = it.id,
                            title = it.title,
                            description = it.description,
                            periodic = it.periodic,
                            begin = it.begin,
                            end = it.end,
                            day = calendar.julianDay,
                            status = Status.Todo,
                        )
                    }

                addAllInstanceUseCase(instances)
            }

            else -> return Result.failure()
        }

        return Result.success()
    }
}
