package wing.tree.pacemaker.extension

import android.icu.util.Calendar
import wing.tree.pacemaker.data.extension.julianDay

val Long.julianDay: Int get() = Calendar.getInstance().let {
    it.timeInMillis = this
    it.julianDay
}