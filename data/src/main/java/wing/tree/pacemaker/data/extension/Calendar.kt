package wing.tree.pacemaker.data.extension

import android.icu.util.Calendar

var Calendar.date: Int
    get() = get(Calendar.DATE)
    set(value) {
        set(Calendar.DATE, value)
    }

var Calendar.julianDay: Int
    get() = get(Calendar.JULIAN_DAY)
    set(value) {
        set(Calendar.JULIAN_DAY, value)
    }

var Calendar.month: Int
    get() = get(Calendar.MONTH)
    set(value) {
        set(Calendar.MONTH, value)
    }

var Calendar.year: Int
    get() = get(Calendar.YEAR)
    set(value) {
        set(Calendar.YEAR, value)
    }
