package com.hxw.frame.base

import android.os.Bundle
import com.hxw.frame.di.AppComponent

/**
 * Created by hxw on 2017/8/29.
 */
interface IFragment {

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
    fun init(savedInstanceState: Bundle)



}