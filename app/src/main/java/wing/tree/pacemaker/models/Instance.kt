package wing.tree.pacemaker.models

import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.entities.Status
import wing.tree.pacemaker.domain.entities.Time
import wing.tree.pacemaker.domain.extension.long

data class Instance(
    override val id: Long = ZERO.long,
    override val routineId: Long,
    override val title: String,
    override val description: String,
    override val begin: Time,
    override val end: Time,
    override val day: Int,
    override val status: Status,
) : Instance
