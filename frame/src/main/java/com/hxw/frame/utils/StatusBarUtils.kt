package com.hxw.frame.utils

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.annotation.NonNull
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * 状态栏工具
 * @author hxw
 * @date 2018/3/8.
 */
object StatusBarUtils {

    /**
     * 沉浸式状态栏
     */
    @JvmStatic
    fun noStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = Color.TRANSPARENT
            activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        //这是把状态栏顶上去,轻触下拉时会下来的,一些效果上可以参考
//        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 全屏,并且沉侵式状态栏
     *
     * @param activity
     */
    @JvmStatic
    fun statuInScreen(activity: Activity) {
        val attrs = activity.window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        activity.window.attributes = attrs
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * 设置状态栏黑色字体图标，
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     *
     * @param activity 需要被处理的 Activity
     */
    @JvmStatic
    fun setStatusBarDarkMode(@NonNull activity: Activity, dark: Boolean): Boolean {

        // 无语系列：ZTK C2016只能时间和电池图标变色。。。。
        if (SystemUtils.isZTKC2016()) {
            return false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (SystemUtils.isMIUI() && isMIUICustomStatusBarLightModeImpl()) {
                return setMIUIStatusBarDarkMode(activity.window, dark)
            } else if (SystemUtils.isFlyme()) {
                return setFlymeStatusBarDarkMode(activity.window, dark)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setAndroid6StatusBarLightMode(activity.window, dark)
                return true
            }
        }
        return false
    }

    /**
     * 更改状态栏图标、文字颜色的方案是否是MIUI自家的， MIUI9之后用回Android原生实现
     * 见小米开发文档说明：https://dev.mi.com/console/doc/detail?pId=1159
     */
    private fun isMIUICustomStatusBarLightModeImpl(): Boolean {
        return SystemUtils.isMIUIV6() ||
                SystemUtils.isMIUIV7() || SystemUtils.isMIUIV8()
    }

    /**
     * 设置状态栏字体图标为深色，需要 MIUIV6 以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回 true
     */
    private fun setMIUIStatusBarDarkMode(window: Window, dark: Boolean): Boolean {
        var result = false
        val clazz = window.javaClass
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为 Flyme 用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun setFlymeStatusBarDarkMode(window: Window, dark: Boolean): Boolean {

        // flyme 在 6.2.0.0A 支持了 Android 官方的实现方案，旧的方案失效
        setAndroid6StatusBarLightMode(window, dark)

        var result = false
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 设置状态栏字体图标为深色，Android 6
     *
     * @param window 需要设置的窗口
     * @param dark  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @TargetApi(23)
    private fun setAndroid6StatusBarLightMode(window: Window, dark: Boolean): Boolean {
        val decorView = window.decorView
        var systemUi = if (dark) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        systemUi = changeStatusBarModeRetainFlag(window, systemUi)
        decorView.systemUiVisibility = systemUi
        return true
    }

    @TargetApi(23)
    private fun changeStatusBarModeRetainFlag(window: Window, out: Int): Int {
        var out1 = out
        out1 = retainSystemUiFlag(window, out1, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        out1 = retainSystemUiFlag(window, out1, View.SYSTEM_UI_FLAG_FULLSCREEN)
        out1 = retainSystemUiFlag(window, out1, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        out1 = retainSystemUiFlag(window, out1, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        out1 = retainSystemUiFlag(window, out1, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        out1 = retainSystemUiFlag(window, out1, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        return out1
    }

    private fun retainSystemUiFlag(window: Window, out: Int, type: Int): Int {
        var out1 = out
        val now = window.decorView.systemUiVisibility
        if (now and type == type) {
            out1 = out1 or type
        }
        return out1
    }
}