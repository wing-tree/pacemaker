package wing.tree.pacemaker.domain.extension

import wing.tree.pacemaker.domain.constant.TWO

val Int.float: Float get() = toFloat()
val Int.half: Int get() = div(TWO)
val Int.long: Long get() = toLong()
