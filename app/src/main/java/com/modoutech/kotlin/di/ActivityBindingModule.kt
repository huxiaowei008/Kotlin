package com.modoutech.kotlin.di

import com.hxw.frame.di.scope.ActivityScope
import com.modoutech.kotlin.mvp.login.LoginActivity
import com.modoutech.kotlin.mvp.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * 绑定对应activity
 * @author hxw
 * @date 2017/9/20
 */
@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    abstract fun loginActivity(): LoginActivity
}