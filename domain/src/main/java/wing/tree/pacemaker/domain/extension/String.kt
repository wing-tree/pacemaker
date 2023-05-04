package wing.tree.pacemaker.domain.extension

val String.int: Int get() = toInt()
val String.intOrNull: Int? get() = toIntOrNull()
