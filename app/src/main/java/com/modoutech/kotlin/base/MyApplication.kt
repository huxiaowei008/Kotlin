package com.modoutech.kotlin.base

import android.app.Application

/**
 * @author hxw
 * @date 2017/9/20
 */
class MyApplication : Application() {

//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerAppComponent
//                .builder()
//                .frameComponent(AppDelegate.FRAME_COMPONENT)
//                .build()
//
//    }
//
//    private var mAppDelegate: AppLifecycle by Delegates.notNull()
//
//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        mAppDelegate = AppDelegate(base)
//        mAppDelegate.attachBaseContext(base)
//    }
//
//    override fun onCreate() {
//        //AppDelegate.FRAME_COMPONENT在onCreate中初始化,applicationInjector在super.onCreate()中就会可能调用到
//        mAppDelegate.onCreate(this)
//        super.onCreate()
//
//    }
//
//    override fun onTerminate() {
//        mAppDelegate.onTerminate(this)
//        super.onTerminate()
//
//    }
}