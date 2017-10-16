package com.hxw.frame.mvp

/**
 * @author hxw
 * @date 2017/9/15
 */
interface IView<P> {

    /**
     * 显示信息
     */
    fun showMessage(message: String)
}