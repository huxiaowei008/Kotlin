package com.hxw.frame.http

import android.content.Context

/**
 * 统一错误处理
 * @author hxw
 * @date 2017/8/18
 */
interface  OnResponseErrorListener {
    fun handleResponseError(context: Context,throwable: Throwable)
}