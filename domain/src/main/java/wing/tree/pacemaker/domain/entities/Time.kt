package wing.tree.pacemaker.domain.entities

interface Time {
    val hour: Int
    val hourOfDay: Int
    val minute: Int
    val amPm: Int
}
