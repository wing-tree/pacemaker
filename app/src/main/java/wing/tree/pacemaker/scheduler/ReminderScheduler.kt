package wing.tree.pacemaker.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import wing.tree.pacemaker.data.extension.hour
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.millisecond
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.second
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.receiver.AlarmReceiver

class ReminderScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun scheduleReminder(instance: Instance) {
        /**
         * Reminder 시간과 시작 시간이 하루 차이, ex) remindAt: pm 11:00, startDay/begin: am 1:00
         * 인 경우, 리마인더 시간 + 24를 한다. 최초 등록 시점에서는 입력된 시간에 바로 리마인더 설정하면되고, 이미 지난 시간이면 무시하면 됨.
         */
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, instance.requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        val calendar = Calendar.getInstance().apply {
            julianDay = instance.day
            second = ZERO
            millisecond = ZERO
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

    fun cancelReminder(requestCode: Int) {
        val operation = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        alarmManager.cancel(operation)
    }

    private val Instance.requestCode: Int get() = id.toInt()
}
