package wing.tree.pacemaker.data.model

import wing.tree.pacemaker.domain.entity.Time

data class Time(
    override val hourOfDay: Int,
    override val minute: Int,
) : Time
