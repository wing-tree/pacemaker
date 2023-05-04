package wing.tree.pacemaker.data.function

import android.icu.util.Calendar
import wing.tree.pacemaker.data.extension.julianDay

fun calendarOf(timeInMillis: Long? = null) = Calendar.getInstance().apply {
    timeInMillis?.let {
        this.timeInMillis = it
    }
}

fun calendarOf(julianDay: Int? = null) = Calendar.getInstance().apply {
    julianDay?.let {
        this.julianDay = it
    }
}
