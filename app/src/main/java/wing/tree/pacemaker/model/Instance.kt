package wing.tree.pacemaker.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.extension.int
import wing.tree.pacemaker.domain.extension.long

@Parcelize
data class Instance(
    override val id: Long = ZERO.long,
    override val routineId: Long,
    override val title: String,
    override val description: String,
    override val begin: Time,
    override val end: Time,
    override val day: Int,
    override val status: Status,
    override val reminder: Reminder,
) : Instance, Parcelable {
    @IgnoredOnParcel
    val notificationId: Int get() = id.int

    companion object {
        const val EXTRA_INSTANCE = "extra.instance"
    }
}
