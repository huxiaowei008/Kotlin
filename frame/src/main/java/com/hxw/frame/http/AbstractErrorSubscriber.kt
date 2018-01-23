package com.hxw.frame.http

import android.net.ParseException
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.hxw.frame.utils.UIUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 实现了错误结果的订阅
 * @author hxw
 * @date 2017/8/19
 */
abstract class AbstractErrorSubscriber<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }
    override fun onError(e: Throwable) {
        Timber.tag("Catch-Error").w(e.message)
        val msg = when (e) {
            is UnknownHostException -> "网络不可用"
            is SocketTimeoutException -> "请求网络超时"
            is HttpException -> convertStatusCode(e)
            is JsonParseException, is ParseException, is JSONException, is JsonIOException
            -> "数据解析错误"
            else -> "未知错误"
        }
        UIUtils.showSnackBar(msg)
    }

    override fun onComplete() {

    }

    private fun convertStatusCode(httpException: HttpException): String =
            when (httpException.code()) {
                307 -> "请求被重定向到其他页面"
                403 -> "请求被服务器拒绝"
                404 -> "请求地址不存在"
                500 -> "服务器发生错误"
                else -> httpException.message()
            }
}