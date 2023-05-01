package wing.tree.pacemaker.domain.entities

enum class Status {
    Todo,
    Doing,
    Done;

    val next: Status get() = when(this) {
        Todo -> Doing
        Doing -> Done
        Done -> Todo
    }
}
