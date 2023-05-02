package wing.tree.pacemaker.schedulers

import android.content.Context
import android.icu.util.Calendar
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.extension.long
import wing.tree.pacemaker.workers.CreateInstancesWorker
import java.util.concurrent.TimeUnit

class WorkScheduler {
    fun scheduleCreateInstancesWorker(context: Context) {
        val calendar = Calendar.getInstance().apply {
            clear(Calendar.HOUR_OF_DAY)
            clear(Calendar.HOUR)
            clear(Calendar.MINUTE)
            clear(Calendar.SECOND)
            clear(Calendar.MILLISECOND)
        }

        val now = Calendar.getInstance().apply {
            if (after(calendar)) {
                add(Calendar.DATE, ONE)
            }
        }

        val uniqueWorkName = CreateInstancesWorker::class.java.name
        val initialDelay = calendar.timeInMillis.minus(now.timeInMillis)
        val periodicWorkRequest = PeriodicWorkRequestBuilder<CreateInstancesWorker>(
            repeatInterval = ONE.long,
            repeatIntervalTimeUnit = TimeUnit.DAYS,
        ).setInitialDelay(
            duration = initialDelay,
            timeUnit = TimeUnit.MILLISECONDS,
        ).build()

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest,
            )
    }
}