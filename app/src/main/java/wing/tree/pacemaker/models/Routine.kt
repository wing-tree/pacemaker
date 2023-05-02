package wing.tree.pacemaker.models

import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.entities.Time

data class Routine(
    override val title: String,
    override val description: String,
    override val periodic: Periodic,
    override val startDay: Int,
    override val endDay: Int,
    override val begin: Time,
    override val end: Time,
) : Routine()
