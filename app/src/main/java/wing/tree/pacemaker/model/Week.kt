package wing.tree.pacemaker.model

import android.icu.util.Calendar
import kotlinx.collections.immutable.toImmutableList
import wing.tree.pacemaker.domain.constant.DAYS_PER_WEEK
import wing.tree.pacemaker.domain.constant.ONE

data class Week(
    val year: Int,
    val month: Month,
    val weekOfMonth: Int,
) {
    val days = with(Calendar.getInstance()) {
        set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        set(Calendar.MONTH, month.month)
        set(Calendar.WEEK_OF_MONTH, weekOfMonth)
        set(Calendar.YEAR, year)
        clear(Calendar.HOUR_OF_DAY)
        clear(Calendar.MILLISECOND)
        clear(Calendar.MINUTE)
        clear(Calendar.SECOND)

        List(DAYS_PER_WEEK) {
            val day = Day(
                dayOfMonth = get(Calendar.DAY_OF_MONTH),
                dayOfWeek = get(Calendar.DAY_OF_WEEK),
                julianDay = get(Calendar.JULIAN_DAY),
                month = get(Calendar.MONTH),
            )

            add(Calendar.DATE, ONE)

            day
        }.toImmutableList()
    }
}
