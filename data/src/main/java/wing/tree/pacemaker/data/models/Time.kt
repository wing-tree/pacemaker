package wing.tree.pacemaker.data.models

import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entities.Time

data class Time(
    override val hour: Int,
    override val hourOfDay: Int,
    override val minute: Int,
    override val amPm: Int,
) : Time {

}
