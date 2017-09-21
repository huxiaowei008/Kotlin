package com.modoutech.kotlin.base

import android.content.Context
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.base.delegate.AppLifecycle
import com.modoutech.kotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlin.properties.Delegates

/**
 * Created by hxw on 2017/9/20.
 */
class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .frameComponent(AppDelegate.FRAME_COMPONENT)
                .build()

    }

    private var mAppDelegate: AppLifecycle by Delegates.notNull()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        mAppDelegate = AppDelegate(base)
        mAppDelegate.attachBaseContext(base)
    }

    override fun onCreate() {
        //AppDelegate.FRAME_COMPONENT在onCreate中初始化,applicationInjector在super.onCreate()中就会可能调用到
        mAppDelegate.onCreate(this)
        super.onCreate()

    }

    override fun onTerminate() {
        mAppDelegate.onTerminate(this)
        super.onTerminate()

    }
}