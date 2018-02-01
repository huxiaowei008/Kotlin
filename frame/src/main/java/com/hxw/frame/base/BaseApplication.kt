package com.hxw.frame.base

import android.app.Application
import android.content.Context
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.base.delegate.AppLifecycle
import kotlin.properties.Delegates

/**
 * 基类Application,可以实现自己的application,把这里的代码复制过去就好
 * @author hxw
 * @date 2017/8/19
 */
class BaseApplication : Application() {

    private var mAppDelegate: AppLifecycle by Delegates.notNull()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        mAppDelegate = AppDelegate(base)
        mAppDelegate.attachBaseContext(base)
    }

    override fun onCreate() {
        mAppDelegate.onCreate(this)
        super.onCreate()

    }

    override fun onTerminate() {
        mAppDelegate.onTerminate(this)
        super.onTerminate()

    }


}