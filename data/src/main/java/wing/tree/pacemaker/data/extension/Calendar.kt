package wing.tree.pacemaker.data.extension

import android.icu.util.Calendar

var Calendar.amPm: Int
    get() = get(Calendar.AM_PM)
    set(value) {
        set(Calendar.AM_PM, value)
    }

var Calendar.hour: Int
    get() = get(Calendar.HOUR)
    set(value) {
        set(Calendar.HOUR, value)
    }

var Calendar.hourOfDay: Int
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) {
        set(Calendar.HOUR_OF_DAY, value)
    }

var Calendar.minute: Int
    get() = get(Calendar.MINUTE)
    set(value) {
        set(Calendar.MINUTE, value)
    }

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
