package wing.tree.pacemaker.model

import wing.tree.pacemaker.domain.entity.Routine
import wing.tree.pacemaker.domain.entity.Time

data class Routine(
    override val title: String,
    override val description: String,
    override val startDay: Int,
    override val endDay: Int,
    override val begin: Time,
    override val end: Time,
    override val reminder: Reminder,
) : Routine()
