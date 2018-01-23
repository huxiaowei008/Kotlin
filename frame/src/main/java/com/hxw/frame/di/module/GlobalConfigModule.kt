package com.hxw.frame.di.module

import android.app.Application
import com.hxw.frame.imageloader.IImageLoader
import com.hxw.frame.imageloader.glide.GlideLoader
import com.hxw.frame.utils.FileUtils
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import java.io.File
import javax.inject.Singleton

/**
 * 配置Module
 * @author hxw
 * @date 2017/8/18
 */
@Module
class GlobalConfigModule(builder: Builder) {
    private var apiUrl = builder.apiUrl
    private var cacheFile = builder.cacheFile
    private var imgLoader = builder.imgLoader
    private var gsonConfiguration = builder.gsonConfiguration
    private var retrofitConfiguration = builder.retrofitConfiguration
    private var okHttpConfiguration = builder.okHttpConfiguration


    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl = apiUrl ?: throw NullPointerException("baseUrl == null")

    @Singleton
    @Provides
    fun provideCacheFile(application: Application) = cacheFile ?: FileUtils.getCacheFile(application)

    @Singleton
    @Provides
    fun provideImageLoader(): IImageLoader = imgLoader ?: GlideLoader()

    @Singleton
    @Provides
    fun provideGsonConfiguration() = gsonConfiguration

    @Singleton
    @Provides
    fun provideRetrofitConfiguration() = retrofitConfiguration

    @Singleton
    @Provides
    fun provideOkhttpConfiguration() = okHttpConfiguration

    companion object Builder {
        private var apiUrl = HttpUrl.parse("https://api.github.com/")
        private var cacheFile: File? = null
        private var imgLoader: IImageLoader? = null
        private var gsonConfiguration: ClientModule.GsonConfiguration? = null
        private var retrofitConfiguration: ClientModule.RetrofitConfiguration? = null
        private var okHttpConfiguration: ClientModule.OkHttpConfiguration? = null

        fun baseUrl(baseUrl: String): Builder {
            this.apiUrl = HttpUrl.parse(baseUrl)
            return this
        }

        fun cacheFile(cacheFile: File): Builder {
            this.cacheFile = cacheFile
            return this
        }

        //用来请求网络图片
        fun imageLoader(loader: IImageLoader): Builder {
            this.imgLoader = loader
            return this
        }

        //retrofit补充配置
        fun retrofitConfiguration(configuration: ClientModule.RetrofitConfiguration): Builder {
            this.retrofitConfiguration = configuration
            return this
        }

        //okHttp补充配置
        fun okhttpConfiguration(configuration: ClientModule.OkHttpConfiguration): Builder {
            this.okHttpConfiguration = configuration
            return this
        }

        //Gson补充配置
        fun gsonConfiguration(configuration: ClientModule.GsonConfiguration): Builder {
            this.gsonConfiguration = configuration
            return this
        }

        fun build() = GlobalConfigModule(this)
    }
}