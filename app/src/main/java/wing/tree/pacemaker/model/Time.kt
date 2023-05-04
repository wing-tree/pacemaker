package wing.tree.pacemaker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Time

@Parcelize
data class Time(
    override val hour: Int,
    override val hourOfDay: Int,
    override val minute: Int,
    override val amPm: Int
) : Time, Parcelable {
    companion object {
        val none = Time(
            hour = ZERO,
            hourOfDay = ZERO,
            minute = ZERO,
            amPm = ZERO,
        )
    }
}
