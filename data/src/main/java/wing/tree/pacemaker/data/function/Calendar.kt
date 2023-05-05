package wing.tree.pacemaker.data.function

import android.icu.util.Calendar

fun calendarOf(timeInMillis: Long? = null): Calendar = Calendar.getInstance().apply {
    timeInMillis?.let {
        this.timeInMillis = it
    }
}
