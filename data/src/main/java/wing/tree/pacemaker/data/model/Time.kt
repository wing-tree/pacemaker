package wing.tree.pacemaker.data.model

import wing.tree.pacemaker.domain.entity.Time

data class Time(
    override val hour: Int,
    override val hourOfDay: Int,
    override val minute: Int,
    override val amPm: Int,
) : Time {

}
