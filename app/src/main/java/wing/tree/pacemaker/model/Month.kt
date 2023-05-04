package wing.tree.pacemaker.model

import kotlinx.collections.immutable.toImmutableList
import wing.tree.pacemaker.domain.constant.WEEKS_PER_MONTH

data class Month(
    val year: Int,
    val month: Int
) {
    val weeks = List(WEEKS_PER_MONTH) {
        Week(
            year = year,
            month = this,
            weekOfMonth = it.inc()
        )
    }.toImmutableList()
}
