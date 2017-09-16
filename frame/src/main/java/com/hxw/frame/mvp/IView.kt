package com.hxw.frame.mvp

/**
 * Created by hxw on 2017/9/15.
 */
interface IView<P> {

    /**
     * 显示信息
     */
    fun showMessage(message: String)
}