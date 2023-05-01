package wing.tree.pacemaker.domain.extension

import wing.tree.pacemaker.domain.constant.TWO

val Float.half: Float get() = div(TWO)
