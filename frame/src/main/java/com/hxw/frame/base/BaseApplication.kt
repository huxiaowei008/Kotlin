package com.hxw.frame.base

import android.app.Application
import android.content.Context
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.base.delegate.AppLifecycle
import kotlin.properties.Delegates

/**
 * 基类application,可以实现自己的application,把这里的代码复制过去就好
 * Created by hxw on 2017/8/19.
 */
class BaseApplication : Application() {

    private var mAppDelegate: AppLifecycle by Delegates.notNull()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mAppDelegate = AppDelegate(base ?: baseContext)
        mAppDelegate.attachBaseContext(base ?: baseContext)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }


}