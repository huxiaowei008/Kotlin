package com.modoutech.kotlin.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.modoutech.kotlin.BuildConfig

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

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this@MyApplication) // 尽可能早，推荐在Application中初始化
    }
}