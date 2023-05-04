package wing.tree.pacemaker.domain.extension

import wing.tree.pacemaker.domain.constant.TWO
import wing.tree.pacemaker.domain.constant.ZERO

val Float.half: Float get() = div(TWO)
val Float.isZero: Boolean get() = this == ZERO.float
