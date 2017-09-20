package com.modoutech.kotlin.base

import android.app.Application
import android.content.Context
import android.net.ParseException
import android.support.v4.app.FragmentManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.hxw.frame.base.delegate.AppLifecycle
import com.hxw.frame.di.module.ClientModule
import com.hxw.frame.di.module.GlobalConfigModule
import com.hxw.frame.http.OnResponseErrorListener
import com.hxw.frame.integration.ConfigModule
import com.hxw.frame.utils.NullStringToEmptyFactory
import com.hxw.frame.utils.UIUtils
import com.modoutech.kotlin.BuildConfig
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by hxw on 2017/8/29.
 */
class GlobalConfiguration : ConfigModule {
    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.responseErrorListener(object : OnResponseErrorListener {
            override fun handleResponseError(context: Context, throwable: Throwable) {
                Timber.tag("Catch-Error").w(throwable.message)
                val msg = when (throwable) {
                    is UnknownHostException -> "网络不可用"
                    is SocketTimeoutException -> "请求网络超时"
                    is HttpException -> convertStatusCode(throwable)
                    is JsonParseException, is ParseException, is JSONException, is JsonIOException
                    -> "数据解析错误"
                    else -> "未知错误"
                }
                UIUtils.showSnackBar(msg)
            }

        }).gsonConfiguration(object : ClientModule.GsonConfiguration {
            override fun configGson(context: Context, builder: GsonBuilder) {
                builder.setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .registerTypeAdapterFactory(NullStringToEmptyFactory<String>())
            }
        })
    }

    override fun injectAppLifecycle(context: Context, lifecycle: MutableList<AppLifecycle>) {
        lifecycle.add(object : AppLifecycle {
            override fun attachBaseContext(base: Context) {

            }

            override fun onCreate(application: Application) {

                //Timber日志打印
                if (BuildConfig.LOG_DEBUG) {
                    Timber.plant(Timber.DebugTree())
                }
            }

            override fun onTerminate(application: Application) {

            }

        })
    }

    override fun injectActivityLifecycle(context: Context, lifecycle: MutableList<Application.ActivityLifecycleCallbacks>) {

    }

    override fun injectFragmentLifecycle(context: Context, lifecycle: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {

    }

    fun convertStatusCode(httpException: HttpException): String =
            when (httpException.code()) {
                307 -> "请求被重定向到其他页面"
                403 -> "请求被服务器拒绝"
                404 -> "请求地址不存在"
                500 -> "服务器发生错误"
                else -> httpException.message()
            }
}