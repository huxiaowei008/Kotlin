package com.hxw.frame.base.delegate

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import com.hxw.frame.base.IActivity
import com.hxw.frame.integration.AppManager
import com.hxw.frame.integration.ConfigModule
import com.hxw.frame.integration.lifecycle.ActivityLifecycleable
import com.hxw.frame.integration.lifecycle.FragmentLifecycleable
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import timber.log.Timber

/**
 * activity的生命周期回调监听
 * @author hxw
 * @date 2017/8/29
 */
class ActivityLifecycle(private val modules: MutableList<ConfigModule>,
                        private val mApplication: Application) : Application.ActivityLifecycleCallbacks {
    //fragment的生命外部拓展
    private var fragmentLifecycle: MutableList<FragmentManager.FragmentLifecycleCallbacks>? = null

    //fragment的生命本框架内部代码的实现
    private val mFragmentLifecycle: FragmentManager.FragmentLifecycleCallbacks by lazy {
        FragmentLifecycle()
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Timber.w("$activity - onActivityCreated")
        AppManager.addActivity(activity)
        if (activity is ActivityLifecycleable) {
            activity.lifecycleSubject().onNext(ActivityEvent.CREATE)
        }

        val useFragment = activity is IActivity && activity.useFragment()
        if (activity is FragmentActivity && useFragment) {
            //注册内部代码
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycle, true)

            if (fragmentLifecycle == null) {
                fragmentLifecycle = mutableListOf()
                modules.forEach { it.injectFragmentLifecycle(mApplication, fragmentLifecycle!!) }
            }
            //注册拓展的代码
            fragmentLifecycle?.forEach {
                activity.supportFragmentManager
                        .registerFragmentLifecycleCallbacks(it, true)
            }
        }

    }

    override fun onActivityStarted(activity: Activity?) {
        Timber.w("$activity - onActivityStarted")
        if (activity is ActivityLifecycleable) {
            activity.lifecycleSubject().onNext(ActivityEvent.START)
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        Timber.w("$activity - onActivityResumed")
        AppManager.setCurrentActivity(activity)
        if (activity is ActivityLifecycleable) {
            activity.lifecycleSubject().onNext(ActivityEvent.RESUME)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        Timber.w("$activity - onActivityPaused")
        if (activity is ActivityLifecycleable) {
            activity.lifecycleSubject().onNext(ActivityEvent.PAUSE)
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        Timber.w("$activity - onActivityStopped")
        if (AppManager.getCurrentActivity() == activity) {
            AppManager.setCurrentActivity(null)
        }
        if (activity is ActivityLifecycleable) {
            activity.lifecycleSubject().onNext(ActivityEvent.STOP)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Timber.w("$activity - onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Timber.w("$activity - onActivityDestroyed")
        AppManager.removeActivity(activity)
        if (activity is ActivityLifecycleable) {
            activity.lifecycleSubject().onNext(ActivityEvent.DESTROY)
        }
    }

    class FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
            super.onFragmentPreAttached(fm, f, context)
            Timber.w("$f - onFragmentPreAttached")
        }

        override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
            super.onFragmentAttached(fm, f, context)
            Timber.w("$f - onFragmentAttached")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.ATTACH)
            }
        }

        override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
            super.onFragmentCreated(fm, f, savedInstanceState)
            Timber.w("$f - onFragmentCreated")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.CREATE)
            }
        }

        override fun onFragmentActivityCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState)
            Timber.w("$f - onFragmentActivityCreated")
        }

        override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            Timber.w("$f - onFragmentViewCreated")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.CREATE_VIEW)
            }
        }

        override fun onFragmentStarted(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentStarted(fm, f)
            Timber.w("$f - onFragmentStarted")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.START)
            }
        }

        override fun onFragmentResumed(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentResumed(fm, f)
            Timber.w("$f - onFragmentResumed")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.RESUME)
            }
        }

        override fun onFragmentPaused(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentPaused(fm, f)
            Timber.w("$f - onFragmentPaused")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.PAUSE)
            }
        }

        override fun onFragmentStopped(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentStopped(fm, f)
            Timber.w("$f - onFragmentStopped")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.STOP)
            }
        }

        override fun onFragmentSaveInstanceState(fm: FragmentManager?, f: Fragment?, outState: Bundle?) {
            super.onFragmentSaveInstanceState(fm, f, outState)
            Timber.w("$f - onFragmentSaveInstanceState")
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentViewDestroyed(fm, f)
            Timber.w("$f - onFragmentViewDestroyed")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.DESTROY_VIEW)
            }
        }

        override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentDestroyed(fm, f)
            Timber.w("$f - onFragmentDestroyed")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.DESTROY)
            }
        }

        override fun onFragmentDetached(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentDetached(fm, f)
            Timber.w("$f - onFragmentDetached")
            if (f is FragmentLifecycleable) {
                f.lifecycleSubject().onNext(FragmentEvent.DETACH)
            }
        }
    }
}