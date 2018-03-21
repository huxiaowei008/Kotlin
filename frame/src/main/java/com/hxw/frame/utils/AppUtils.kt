package com.hxw.frame.utils

import android.content.Context


/**
 * app相关工具类
 * @author hxw
 * @date 2017/10/18
 */
object AppUtils {

}

fun Context.getVersionCode(): Int = try {
    val info = this.packageManager.getPackageInfo(this.packageName, 0)
    info.versionCode
} catch (e: Exception) {
    e.printStackTrace()
    0
}


fun Context.getVersionName(): String = try {
    val info = this.packageManager.getPackageInfo(this.packageName, 0)
    info.versionName
} catch (e: Exception) {
    e.printStackTrace()
    ""
}
