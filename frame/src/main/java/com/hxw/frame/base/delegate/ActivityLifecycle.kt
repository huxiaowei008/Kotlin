package com.hxw.frame.base.delegate

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.hxw.frame.base.IActivity
import com.hxw.frame.integration.AppManager
import com.hxw.frame.integration.ConfigModule
import timber.log.Timber

/**
 * Activity的生命周期回调监听
 * @author hxw
 * @date 2017/8/29
 */
class ActivityLifecycle(private val modules: MutableList<ConfigModule>,
                        private val mApplication: Application) : Application.ActivityLifecycleCallbacks {
    //fragment的生命外部拓展
    private val fragmentLifecycle: MutableList<FragmentManager.FragmentLifecycleCallbacks> by lazy {
        mutableListOf<FragmentManager.FragmentLifecycleCallbacks>()
    }

    //fragment的生命本框架内部代码的实现
    private val mFragmentLifecycle: FragmentManager.FragmentLifecycleCallbacks by lazy {
        FragmentLifecycle()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.w("$activity - onActivityCreated")
        AppManager.addActivity(activity)

        val useFragment = activity is IActivity && activity.useFragment()
        if (activity is FragmentActivity && useFragment) {
            //注册内部代码
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycle, true)

            modules.forEach {
                it.injectFragmentLifecycle(mApplication, fragmentLifecycle)
            }
            //注册拓展的代码
            fragmentLifecycle.forEach {
                activity.supportFragmentManager
                        .registerFragmentLifecycleCallbacks(it, true)
            }
        }

    }

    override fun onActivityStarted(activity: Activity) {
        Timber.w("$activity - onActivityStarted")
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.w("$activity - onActivityResumed")
        AppManager.setCurrentActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.w("$activity - onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.w("$activity - onActivityStopped")
        if (AppManager.getCurrentActivity() == activity) {
            AppManager.setCurrentActivity(null)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
        Timber.w("$activity - onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.w("$activity - onActivityDestroyed")
        AppManager.removeActivity(activity)
    }
}