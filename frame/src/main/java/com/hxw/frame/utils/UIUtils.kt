package com.hxw.frame.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
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
    private val mToast: Toast by lazy { Toast.makeText(AppDelegate.instance, "", Toast.LENGTH_SHORT) }

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
    fun toast(message: String) {
        mToast.setText(message)
        mToast.show()
    }
}

/**
 * dp转px
 *
 * @param dpValue dp值
 * @return px值
 */
fun Context.dpToPx(dpValue: Float): Int {
    val scale = this.resources.displayMetrics.scaledDensity
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * sp转px
 *
 * @param spValue sp值
 * @return px值
 */
fun Context.spToPx(spValue: Float): Int {
    val scale = this.resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}