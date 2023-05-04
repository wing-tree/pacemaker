package wing.tree.pacemaker.model

import wing.tree.pacemaker.domain.entity.Reminder

data class Reminder(
    override val hoursBefore: Int,
    override val minutesBefore: Int,
    override val on: Boolean,
) : Reminder
