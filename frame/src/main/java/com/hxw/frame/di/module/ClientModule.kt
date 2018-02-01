package com.hxw.frame.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hxw.frame.integration.RepositoryManager
import com.hxw.frame.utils.StringUtils
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * 三方客户端Module
 * @author hxw
 * @date 2017/8/18
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
    fun provideClient(application: Application, configuration: OkHttpConfiguration?): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            var str = it
            if ((it.startsWith("{") && it.endsWith("}")) ||
                    (it.startsWith("[") && it.endsWith("]"))) {
                str = StringUtils.jsonFormat(it)
            }
            Timber.tag("OkHttp").d(str)
        })
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

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
    fun provideRepositoryManager(retrofit: Retrofit) = RepositoryManager(retrofit)
}