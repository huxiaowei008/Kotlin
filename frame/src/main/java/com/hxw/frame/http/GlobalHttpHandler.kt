package com.hxw.frame.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * http拦截处理
 * Created by hxw on 2017/8/18.
 */
interface GlobalHttpHandler {
    fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response
    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request
}