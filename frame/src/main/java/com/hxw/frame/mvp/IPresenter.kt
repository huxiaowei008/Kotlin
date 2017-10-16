package com.hxw.frame.mvp

/**
 * @author hxw
 * @date 2017/9/15
 */
interface IPresenter<V> {
    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    fun injectView(view: V)
    /**
     * Drops the reference to the view when destroyed
     */
    fun onDestroy()
}