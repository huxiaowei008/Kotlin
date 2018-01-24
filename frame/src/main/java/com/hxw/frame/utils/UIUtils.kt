package com.hxw.frame.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.design.widget.Snackbar
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.integration.AppManager
import timber.log.Timber

/**
 * UI界面工具类
 * @author hxw
 * @date 2017/8/19
 */
object UIUtils {

    @JvmStatic
    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        val currentActivity = AppManager.getCurrentActivity()
        if (currentActivity == null) {
            Timber.w("mCurrentActivity == null when showSnackBar")
        } else {
            val view: View = currentActivity.window.decorView.findViewById(android.R.id.content)
            Snackbar.make(view, message, duration).show()
        }
    }

    @JvmStatic
    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(AppDelegate.instance, message, duration).show()
    }

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
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    @JvmStatic
    fun dpToPx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    @JvmStatic
    fun spToPx(context: Context, spValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }
}