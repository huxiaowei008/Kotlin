package com.hxw.frame.integration

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.hxw.frame.base.delegate.AppLifecycle
import com.hxw.frame.di.module.GlobalConfigModule

/**
 * 此接口可以给框架配置一些参数,需要实现类实现后,并在AndroidManifest中声明该实现类
 * Created by hxw on 2017/8/19.
 */
interface ConfigModule {

    /**
     * 使用{@link GlobalConfigModule.Builder}给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
    fun applyOptions(context: Context, builder: GlobalConfigModule.Builder)

    /**
     * 使用{@link AppLifecycle}在Application的声明周期中注入一些操作
     *
     * @param context
     * @param lifecycle
     * @return
     */
    fun injectAppLifecycle(context: Context, lifecycle: MutableList<AppLifecycle>)

    /**
     * 使用{@link Application.ActivityLifecycleCallbacks}在Activity的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycle
     */
    fun injectActivityLifecycle(context: Context, lifecycle: MutableList<Application.ActivityLifecycleCallbacks>)

    /**
     * 使用{@link FragmentManager.FragmentLifecycleCallbacks}在Fragment的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycle
     */
    fun injectFragmentLifecycle(context: Context, lifecycle: MutableList<FragmentManager.FragmentLifecycleCallbacks>)
}