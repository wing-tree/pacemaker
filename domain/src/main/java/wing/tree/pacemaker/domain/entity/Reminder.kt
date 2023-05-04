package wing.tree.pacemaker.domain.entity

interface Reminder {
    val hoursBefore: Int
    val minutesBefore: Int
    val on: Boolean
}
