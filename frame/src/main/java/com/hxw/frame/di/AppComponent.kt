package com.hxw.frame.di

import android.app.Application
import com.google.gson.Gson
import com.hxw.frame.di.module.AppModule
import com.hxw.frame.di.module.ClientModule
import com.hxw.frame.di.module.GlobalConfigModule
import com.hxw.frame.http.ErrorHandler
import com.hxw.frame.imageloader.ImageLoader
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File
import javax.inject.Singleton

/**
 * 公用组件
 * Created by hxw on 2017/8/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ClientModule::class, GlobalConfigModule::class))
interface AppComponent {

    fun application(): Application

    fun gson(): Gson

    fun okHttpClient(): OkHttpClient

    fun retrofit(): Retrofit

    //缓存文件根目录(RxCache和Glide的的缓存都已经作为子文件夹在这个目录里),
    //应该将所有缓存放到这个根目录里,便于管理和清理,可在GlobeConfigModule里配置
    fun cacheFile(): File

    fun imageLoader(): ImageLoader

    fun errorHandler(): ErrorHandler
}