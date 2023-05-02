package wing.tree.pacemaker.domain.entities

interface Instance {
    val id: Long
    val routineId: Long
    val title: String
    val description: String
    val begin: Time
    val end: Time
    val day: Int
    val status: Status
}
