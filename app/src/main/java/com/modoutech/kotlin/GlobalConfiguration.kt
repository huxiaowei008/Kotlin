package com.modoutech.kotlin

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.hxw.frame.base.delegate.AppLifecycle
import com.hxw.frame.di.module.GlobalConfigModule
import com.hxw.frame.integration.ConfigModule
import timber.log.Timber

/**
 * Created by hxw on 2017/8/29.
 */
class GlobalConfiguration : ConfigModule {
    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {

    }

    override fun injectAppLifecycle(context: Context, lifecycle: MutableList<AppLifecycle>) {
        lifecycle.add(object : AppLifecycle {
            override fun attachBaseContext(base: Context) {

            }

            override fun onCreate(application: Application) {

                //Timber日志打印
                if (BuildConfig.LOG_DEBUG) {
                    Timber.plant(Timber.DebugTree())
                }
            }

            override fun onTerminate(application: Application) {

            }

        })
    }

    override fun injectActivityLifecycle(context: Context, lifecycle: MutableList<Application.ActivityLifecycleCallbacks>) {

    }

    override fun injectFragmentLifecycle(context: Context, lifecycle: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {

    }


}