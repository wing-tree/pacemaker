package wing.tree.pacemaker.scheduler

import android.content.Context
import android.icu.util.Calendar
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import wing.tree.pacemaker.data.extension.hour
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.millisecond
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.second
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.extension.long
import wing.tree.pacemaker.worker.InstancesGenerationWorker
import java.util.concurrent.TimeUnit

class WorkScheduler {
    fun scheduleCreateInstancesWorker(context: Context) {
        val calendar = Calendar.getInstance().apply {
            hourOfDay = ZERO
            hour = ZERO
            minute = ZERO
            second = ZERO
            millisecond = ZERO
        }

        val now = Calendar.getInstance().apply {
            if (after(calendar)) {
                add(Calendar.DATE, ONE)
            }
        }

        val uniqueWorkName = InstancesGenerationWorker::class.java.name
        val initialDelay = calendar.timeInMillis.minus(now.timeInMillis)
        val periodicWorkRequest = PeriodicWorkRequestBuilder<InstancesGenerationWorker>(
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