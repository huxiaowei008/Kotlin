package com.hxw.frame.http

import io.reactivex.Observer

/**
 * Created by hxw on 2017/8/19.
 */
abstract class ErrorSubscriber<T>(private val handler: ErrorHandler) : Observer<T> {

    override fun onError(e: Throwable) {
        handler.handleError(e)
    }
}