package com.hxw.frame.utils

import android.app.Application
import android.content.Context
import android.widget.Toast

/**
 * Created by hxw on 2017/8/19.
 */
object UIUtils {

    var application: Application? = null

    fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    fun initSingle(application: Application) {
        this.application = if (this.application == null) application
        else throw IllegalStateException("application already initialized")
    }

}