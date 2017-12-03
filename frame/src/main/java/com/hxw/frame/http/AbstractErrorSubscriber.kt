package com.hxw.frame.http

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 实现了错误结果的订阅
 * @author hxw
 * @date 2017/8/19
 */
abstract class AbstractErrorSubscriber<T>(private val handler: ErrorHandler) : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }
    override fun onError(e: Throwable) {
        handler.handleError(e)
    }

    override fun onComplete() {

    }
}