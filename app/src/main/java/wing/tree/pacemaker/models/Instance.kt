package wing.tree.pacemaker.models

import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.entities.Status
import wing.tree.pacemaker.domain.extension.long

data class Instance(
    override val id: Long = ZERO.long,
    override val routineId: Long,
    override val title: String,
    override val description: String,
    override val begin: Long,
    override val end: Long,
    override val day: Int,
    override val status: Status,
    override val periodic: Routine.Periodic,
) : Instance
