package wing.tree.pacemaker.domain.entity

sealed interface TimePeriod {
    data class DateRange(val startDay: Int, val endDay: Int) : TimePeriod
    object Overall : TimePeriod
}
