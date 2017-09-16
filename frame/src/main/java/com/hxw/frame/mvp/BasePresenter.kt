package com.hxw.frame.mvp


/**
 * presenter基类
 * Created by hxw on 2017/9/16.
 */
open class BasePresenter<V> : IPresenter<V> {

    protected var mView: V? = null

    override fun injectView(view: V) {
        this.mView = view
    }

    override fun dropView() {
        mView = null
    }
}