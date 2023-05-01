package wing.tree.pacemaker.domain.entities

interface Instance {
    val id: Long
    val routineId: Long
    val title: String
    val description: String
    val begin: Long
    val end: Long
    val day: Int
    val status: Status
    val periodic: Routine.Periodic
}
