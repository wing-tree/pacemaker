package wing.tree.pacemaker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import wing.tree.pacemaker.domain.entity.Reminder

@Parcelize
data class Reminder(
    override val hoursBefore: Int,
    override val minutesBefore: Int,
    override val on: Boolean,
) : Reminder, Parcelable
