package wing.tree.pacemaker.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import wing.tree.pacemaker.data.extension.cloneAsCalendar
import wing.tree.pacemaker.data.extension.date
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.millisecond
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.second
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.extension.int
import wing.tree.pacemaker.model.Instance
import wing.tree.pacemaker.receiver.AlarmReceiver

class ReminderScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    // TODO 최초등록 로직 추가 필.
    // 더 좋은 방식 있을지도?
    fun scheduleReminder(instance: Instance, isInitialScheduling: Boolean) {
        /**
         * Reminder 시간과 시작 시간이 하루 차이, ex) remindAt: pm 11:00, startDay/begin: am 1:00
         * 인 경우, 리마인더 시간 + 24를 한다. 최초 등록 시점에서는 입력된 시간에 바로 리마인더 설정하면되고, 이미 지난 시간이면 무시하면 됨.
         */
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Instance.EXTRA_INSTANCE, instance)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            instance.requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val calendar = Calendar.getInstance().apply {
            julianDay = instance.day
            hourOfDay = instance.begin.hourOfDay
            minute = instance.begin.minute
            second = ZERO
            millisecond = ZERO
        }

        val trigger = calendar.cloneAsCalendar().apply {
            hourOfDay -= instance.reminder.hoursBefore
            minute -= instance.reminder.minutesBefore
        }

        // TODO 최초 등록 시 무시해야함.
        if (isInitialScheduling.not() && trigger.date < calendar.date) {
            trigger.date += ONE
        }

        val triggerAtMillis = trigger.timeInMillis

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

    private val Instance.requestCode: Int get() = id.int
}
