package com.hxw.frame.http

import io.reactivex.Observer

/**
 * 实现了错误结果的订阅
 * Created by hxw on 2017/8/19.
 */
abstract class AbstractErrorSubscriber<T>(private val handler: ErrorHandler) : Observer<T> {

    override fun onError(e: Throwable) {
        handler.handleError(e)
    }
}