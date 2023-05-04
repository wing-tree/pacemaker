package wing.tree.pacemaker.domain.entity

import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.extension.long

abstract class Routine {
    abstract val title: String
    abstract val description: String
    abstract val startDay: Int
    abstract val endDay: Int
    abstract val begin: Time
    abstract val end: Time
    abstract val reminder: Reminder

    open val id: Long = ZERO.long
}
