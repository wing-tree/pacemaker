package wing.tree.pacemaker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Time

@Parcelize
data class Time(
    override val hourOfDay: Int,
    override val minute: Int,
) : Time, Parcelable {
    companion object {
        val none = Time(
            hourOfDay = ZERO,
            minute = ZERO,
        )
    }
}
