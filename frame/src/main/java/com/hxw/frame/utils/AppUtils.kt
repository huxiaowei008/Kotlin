package com.hxw.frame.utils

import android.content.Context


/**
 * app相关工具类
 * @author hxw
 * @date 2017/10/18
 */
object AppUtils {

    fun getVersionCode(context: Context): Int = try {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        info.versionCode
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }

    fun getVersionName(context: Context): String = try {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        info.versionName
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}