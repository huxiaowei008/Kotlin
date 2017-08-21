package com.hxw.frame.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hxw.frame.http.ErrorHandler
import com.hxw.frame.http.GlobalHttpHandler
import com.hxw.frame.http.OnResponseErrorListener
import com.hxw.frame.http.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 三方客户端Module
 * Created by hxw on 2017/8/18.
 */
@Module
class ClientModule {

    /**
     * 提供Gson
     */
    @Singleton
    @Provides
    fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
        val builder = GsonBuilder()
        configuration?.configGson(application, builder)
        return builder.create()
    }

    /**
     * Gson配置接口
     */
    interface GsonConfiguration {
        fun configGson(context: Context, builder: GsonBuilder)
    }

    /**
     * 提供Retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofit(application: Application, client: OkHttpClient,
                        httpUrl: HttpUrl, gson: Gson, configuration: RetrofitConfiguration?): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(httpUrl)//域名
                .client(client)//设置okhttp
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create(gson))//使用Gson

        configuration?.configRetrofit(application, builder)

        return builder.build()
    }

    /**
     * Retrofit配置接口
     */
    interface RetrofitConfiguration {
        fun configRetrofit(context: Context, builder: Retrofit.Builder)
    }

    /**
     * 提供OkHttp
     */
    @Singleton
    @Provides
    fun provideClient(application: Application, interceptor: Interceptor,
                      configuration: OkHttpConfiguration?, handler: GlobalHttpHandler?): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
//        if (handler != null) {
        builder.addInterceptor { chain ->
            chain.proceed(handler?.
                    onHttpRequestBefore(chain, chain.request()))
        }
//        }
        configuration?.configOkHttp(application, builder)
        return builder.build()
    }

    /**
     * OkHttp配置接口
     */
    interface OkHttpConfiguration {
        fun configOkHttp(context: Context, builder: OkHttpClient.Builder)
    }

    @Singleton
    @Provides
    fun provideIntercept(interceptor: RequestInterceptor): Interceptor = interceptor

    @Singleton
    @Provides
    fun provideErrorHandler(application: Application,
                            onResponseErrorListener: OnResponseErrorListener?)
            = ErrorHandler(application, onResponseErrorListener)
}