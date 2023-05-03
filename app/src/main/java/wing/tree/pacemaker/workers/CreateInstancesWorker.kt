package wing.tree.pacemaker.workers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.entities.Status
import wing.tree.pacemaker.domain.entities.Time
import wing.tree.pacemaker.domain.extension.long
import wing.tree.pacemaker.domain.use.cases.AddAllInstanceUseCase
import wing.tree.pacemaker.domain.use.cases.LoadRoutinesUseCase
import wing.tree.pacemaker.domain.use.cases.core.Result.Complete
import wing.tree.pacemaker.receivers.AlarmReceiver

@HiltWorker
class CreateInstancesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addAllInstanceUseCase: AddAllInstanceUseCase,
    private val loadRoutinesUseCase: LoadRoutinesUseCase,
) : CoroutineWorker(context, workerParams) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override suspend fun doWork(): Result {
        when (val result = loadRoutinesUseCase().firstOrNull()) {
            is Complete.Success -> {
                val calendar = Calendar.getInstance()

                // TODO 설정된 요일도 반영, 위 캘린더 이용, 오늘 무슨 요일인지에 따라, 예약 여부 판단하면 될 듯.
                val instances = result
                    .data
                    .filter {
                        calendar.julianDay in it.startDay..it.endDay
                    }.map {
                        object : Instance {
                            override val id: Long = ZERO.long
                            override val routineId: Long = it.id
                            override val title: String = it.title
                            override val description: String = it.description
                            override val begin: Time = it.begin
                            override val end: Time = it.end
                            override val day: Int = calendar.julianDay
                            override val status: Status = Status.Todo

                        }
                    }

                if (addAllInstanceUseCase(instances).isSuccess) {
                    instances.forEach { instance ->
                        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                        }

                        val triggerAtMillis = calendar.apply {
                            hour = instance.begin.hour
                            hourOfDay = instance.begin.hourOfDay
                            minute = instance.begin.minute
                        }.timeInMillis

                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            triggerAtMillis,
                            pendingIntent
                        )
                    }
                }
            }

            else -> return Result.failure()
        }

        return Result.success()
    }
}
