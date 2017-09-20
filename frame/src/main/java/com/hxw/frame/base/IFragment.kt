package com.hxw.frame.base

import android.os.Bundle

/**
 * fragment基础接口
 * Created by hxw on 2017/8/29.
 */
interface IFragment {

    /**
     * @return 返回布局资源ID
     */
    fun getLayoutId(): Int

    /**
     * @deprecated 使用了dagger2 2.11版本,这接口不需要了
     * 依赖注入的入口,提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
     *
     * @param frameComponent 基础注入器
     */
    //    fun componentInject(frameComponent: FrameComponent)

    /**
     * 初始化，会在onCreate中执行
     */
    fun init(savedInstanceState: Bundle?)


}