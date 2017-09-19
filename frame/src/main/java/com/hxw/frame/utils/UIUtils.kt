package com.hxw.frame.utils

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.integration.AppManager
import timber.log.Timber

/**
 * UI界面工具类
 * Created by hxw on 2017/8/19.
 */
object UIUtils {

    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        if (AppManager.getCurrentActivity() == null) {
            Timber.w("mCurrentActivity == null when showSnackBar")
        } else {
            val view: View = AppManager.getCurrentActivity()!!
                    .window.decorView.findViewById(android.R.id.content)
            Snackbar.make(view, message, duration).show()
        }
    }

    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(AppDelegate.instance, message, duration).show()
    }
}