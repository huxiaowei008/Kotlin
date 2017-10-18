package com.hxw.frame.base.delegate

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * 空的ActivityLifecycleCallbacks实现
 * @author hxw
 * @date 2017/10/17
 */
abstract class BaseActivityLife : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }
}