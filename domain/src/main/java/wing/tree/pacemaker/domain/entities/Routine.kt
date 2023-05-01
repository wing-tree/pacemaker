package wing.tree.pacemaker.domain.entities

import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.extension.long

abstract class Routine {
    abstract val title: String
    abstract val description: String
    abstract val startDay: Int
    abstract val endDay: Int
    abstract val begin: Long
    abstract val end: Long
    abstract val periodic: Periodic

    open val id: Long = ZERO.long

    enum class Periodic {
        DAILY,
        WEEKLY,
        MONTHLY,
    }
}
