package com.hxw.frame.base.delegate

import android.app.Application
import android.content.Context
import com.hxw.frame.base.DelegatesExt
import com.hxw.frame.di.DaggerFrameComponent
import com.hxw.frame.di.FrameComponent
import com.hxw.frame.di.module.AppModule
import com.hxw.frame.di.module.ClientModule
import com.hxw.frame.di.module.GlobalConfigModule
import com.hxw.frame.integration.ConfigModule
import com.hxw.frame.integration.ManifestParser

/**
 * application的代理
 * Created by hxw on 2017/8/28.
 */
class AppDelegate(context: Context) : AppLifecycle {
    companion object {
        var FRAME_COMPONENT: FrameComponent by DelegatesExt.notNullSingleValue()
        var instance: Application by DelegatesExt.notNullSingleValue()

    }

    private val mAppLifecycle: MutableList<AppLifecycle> = mutableListOf()//application的生命内容外部拓展
    //这里的activity生命周期回调是给外面拓展用的
    private val activityLifecycle: MutableList<Application.ActivityLifecycleCallbacks> = mutableListOf()
    //解析清单文件配置的自定义ConfigModule的metadata标签，返回一个ConfigModule集合
    private val mModules: MutableList<ConfigModule> = ManifestParser(context).parse()
    private var mActivityLifecycle: ActivityLifecycle by DelegatesExt.notNullSingleValue()

    init {
        mModules.forEach {
            it.injectAppLifecycle(context, mAppLifecycle)
            it.injectActivityLifecycle(context, activityLifecycle)
        }
    }

    override fun attachBaseContext(base: Context) {
        mAppLifecycle.forEach {
            it.attachBaseContext(base)
        }
    }

    override fun onCreate(application: Application) {
        instance = application
        FRAME_COMPONENT = DaggerFrameComponent
                .builder()
                .appModule(AppModule(application))
                .clientModule(ClientModule())
                .globalConfigModule(getGlobeConfigModule(application, mModules))
                .build()


        mActivityLifecycle = ActivityLifecycle(mModules, application)
        //注册activity生命周期的回调
        application.registerActivityLifecycleCallbacks(mActivityLifecycle)
        activityLifecycle.forEach {
            application.registerActivityLifecycleCallbacks(it)
        }

        mAppLifecycle.forEach {
            it.onCreate(application)
        }
    }

    override fun onTerminate(application: Application) {
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycle)
        if (activityLifecycle.isNotEmpty()) {
            activityLifecycle.forEach {
                application.unregisterActivityLifecycleCallbacks(it)
            }
        }
        if (mAppLifecycle.isNotEmpty()) {
            mAppLifecycle.forEach {
                it.onTerminate(application)
            }
        }

    }

    private fun getGlobeConfigModule(context: Context, modules: List<ConfigModule>): GlobalConfigModule {
        val builder = GlobalConfigModule.Builder
        modules.forEach {
            it.applyOptions(context, builder)
        }
        return builder.build()
    }

}