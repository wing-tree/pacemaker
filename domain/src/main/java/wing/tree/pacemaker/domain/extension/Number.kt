package wing.tree.pacemaker.domain.extension

import wing.tree.pacemaker.domain.constant.ZERO

val Number.isZero: Boolean get() = when(this) {
    is Byte -> this == ZERO.byte
    is Double -> this == ZERO.double
    is Float -> this == ZERO.float
    is Int -> this == ZERO
    is Long -> this == ZERO.long
    is Short -> this == ZERO.short
    else -> false
}
