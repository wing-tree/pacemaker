package wing.tree.pacemaker.extension

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

fun Context.checkPermission(vararg permission: String) = permission.all {
    checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
}
