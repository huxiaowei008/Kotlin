package com.modoutech.kotlin.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatDelegate
import com.google.gson.GsonBuilder
import com.hxw.frame.base.delegate.AppLifecycle
import com.hxw.frame.base.delegate.BaseActivityLifecycleCallbacks
import com.hxw.frame.di.module.ClientModule
import com.hxw.frame.di.module.GlobalConfigModule
import com.hxw.frame.integration.ConfigModule
import com.hxw.frame.utils.NullStringToEmptyFactory
import com.hxw.frame.utils.StatusBarUtils
import com.hxw.frame.utils.UIUtils
import com.modoutech.kotlin.BuildConfig
import timber.log.Timber

/**
 * @author hxw
 * @date 2017/8/29
 */
class GlobalConfiguration : ConfigModule {
    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.gsonConfiguration(object : ClientModule.GsonConfiguration {
            override fun configGson(context: Context, builder: GsonBuilder) {
                builder.setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .registerTypeAdapterFactory(NullStringToEmptyFactory<String>())
            }
        })
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
        lifecycle.add(object : BaseActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
                StatusBarUtils.noStatusBar(activity)
            }
        })
    }

    override fun injectFragmentLifecycle(context: Context, lifecycle: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {

    }


}