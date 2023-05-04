package wing.tree.pacemaker.domain.extension

import wing.tree.pacemaker.domain.constant.TWO
import wing.tree.pacemaker.domain.constant.ZERO

val Int.byte: Byte get() = toByte()
val Int.double: Double get() = toDouble()
val Int.float: Float get() = toFloat()
val Int.half: Int get() = div(TWO)
val Int.isZero: Boolean get() = this == ZERO
val Int.long: Long get() = toLong()
val Int.short: Short get() = toShort()

inline fun <N> N.ifZero(defaultValue: () -> N): N where N : Number = if (isZero) {
    this
} else {
    defaultValue()
}
