package com.hxw.frame.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * presenter基类
 * Created by hxw on 2017/9/16.
 */
open class BasePresenter<V> : IPresenter<V> {

    protected var mView: V? = null
    protected var mCompositeDisposable: CompositeDisposable? = null

    override fun injectView(view: V) {
        this.mView = view
    }

    override fun onDestroy() {
        dispose()
        mView = null
        mCompositeDisposable = null
    }

    /**
     * 添加disposable到compositeDisposable,
     * 统一管理disposable,方便取消订阅
     *
     * @param disposable
     */
    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        //将所有disposable放入,集中处理
        mCompositeDisposable!!.add(disposable)
    }

    fun dispose() {
        if (mCompositeDisposable != null) {
            //保证activity结束时取消所有正在执行的订阅
            mCompositeDisposable!!.clear()
        }
    }
}