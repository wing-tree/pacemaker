package wing.tree.pacemaker.extension

import android.app.Activity

fun Activity.shouldShowRequestPermissionRationale(vararg permission: String) = permission.any {
    shouldShowRequestPermissionRationale(it)
}
