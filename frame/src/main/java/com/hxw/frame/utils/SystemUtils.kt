package com.hxw.frame.utils

import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Method
import java.util.*

/**
 * 系统工具
 * @author hxw
 * @date 2018/3/8.
 */
object SystemUtils {

    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_FLYME_VERSION_NAME = "ro.build.display.id"
    private val FLYME = "flyme"
    private val ZTEC2016 = "zte c2016"
    private val ZUKZ1 = "zuk z1"
    private val MEIZUBOARD = arrayOf("m9", "M9", "mx", "MX")
    private var sMiuiVersionName: String? = null
    private var sFlymeVersionName: String? = null

    init {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
            val properties = Properties()
            properties.load(fileInputStream)
            val clzSystemProperties = Class.forName("android.os.SystemProperties")
            val getMethod = clzSystemProperties.getDeclaredMethod("get", String::class.java)
            // miui
            sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_MIUI_VERSION_NAME)
            //flyme
            sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_FLYME_VERSION_NAME)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            FileUtils.close(fileInputStream)
        }
    }


    /**
     * 判断是否是flyme系统
     */
    @JvmStatic
    fun isFlyme(): Boolean =
            !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName!!.contains(FLYME)

    /**
     * 判断是否是MIUI系统
     */
    fun isMIUI(): Boolean = !TextUtils.isEmpty(sMiuiVersionName)

    fun isMIUIV6(): Boolean = "v6" == sMiuiVersionName

    fun isMIUIV7(): Boolean = "v7" == sMiuiVersionName

    fun isMIUIV8(): Boolean = "v8" == sMiuiVersionName

    fun isMIUIV9(): Boolean = "v9" == sMiuiVersionName

    /**
     * 判断是否为 ZUK Z1 和 ZTK C2016。
     * 两台设备的系统虽然为 android 6.0，但不支持状态栏icon颜色改变，因此经常需要对它们进行额外判断。
     */
    @JvmStatic
    fun isZUKZ1(): Boolean {
        val board = android.os.Build.MODEL
        return board != null && board.toLowerCase().contains(ZUKZ1)
    }

    @JvmStatic
    fun isZTKC2016(): Boolean {
        val board = android.os.Build.MODEL
        return board != null && board.toLowerCase().contains(ZTEC2016)
    }

    private fun getLowerCaseName(p: Properties, get: Method, key: String): String? {
        var name: String? = p.getProperty(key)
        if (name == null) {
            try {
                name = get.invoke(null, key) as String
            } catch (ignored: Exception) {
            }

        }
        if (name != null) name = name.toLowerCase()
        return name
    }
}