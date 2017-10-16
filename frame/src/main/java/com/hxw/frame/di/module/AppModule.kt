package com.hxw.frame.di.module

import android.app.Application
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
}