package com.hxw.frame.base

import android.os.Bundle
import com.hxw.frame.di.AppComponent

/**
 * Created by hxw on 2017/8/29.
 */
interface IActivity {

    /**
     * @return 返回布局资源ID
     */
    fun getLayoutId(): Int

    /**
     * 依赖注入的入口,提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
     *
     * @param appComponent 基础注入器
     */
    fun componentInject(appComponent: AppComponent)

    /**
     * 初始化，会在onCreate中执行
     */
    fun init(savedInstanceState: Bundle?)

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,这个Activity中的Fragment不会执行FragmentLifecycleCallbacks中的代码
     *
     */
    fun useFragment(): Boolean
}