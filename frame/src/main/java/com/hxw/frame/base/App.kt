package com.hxw.frame.base

import com.hxw.frame.di.AppComponent

/**
 * Created by hxw on 2017/8/19.
 */
interface App {
    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     */
    fun getAppComponent(): AppComponent
}