package com.hxw.frame.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 应用Module
 * @author hxw
 * @date 2017/8/18
 */
@Module
class AppModule(private val application: Application) {

    /**
     * 提供application
     */
    @Singleton
    @Provides
    fun provideApplication() = application

    /**
     * 提供存放用户数据的SharedPreferences
     */
    @Singleton
    @Provides
    fun provideUserSharedPreferences(): SharedPreferences
            = application.getSharedPreferences("UserData", Context.MODE_PRIVATE)
}