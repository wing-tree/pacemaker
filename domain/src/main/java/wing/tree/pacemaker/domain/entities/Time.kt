package wing.tree.pacemaker.domain.entities

import wing.tree.pacemaker.domain.constant.ZERO

data class Time(
    val hour: Int,
    val hourOfDay: Int,
    val minute: Int,
    val amPm: Int,
) {
    companion object {
        val none = Time(
            ZERO,
            ZERO,
            ZERO,
            ZERO,
        )
    }
}
