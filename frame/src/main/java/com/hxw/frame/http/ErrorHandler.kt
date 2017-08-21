package com.hxw.frame.http

import android.content.Context

/**
 * Created by hxw on 2017/8/19.
 */
class ErrorHandler(private val context: Context, private val errorListener: OnResponseErrorListener?) {

    fun handleError(throwable: Throwable) = errorListener?.handleResponseError(context, throwable)
}