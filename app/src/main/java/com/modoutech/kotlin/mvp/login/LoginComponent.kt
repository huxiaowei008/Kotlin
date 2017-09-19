package com.modoutech.kotlin.mvp.login

import com.hxw.frame.di.AppComponent
import com.hxw.frame.di.scope.ActivityScope
import dagger.Component

/**
 * Created by hxw on 2017/9/19.
 */
@ActivityScope
@Component(modules = arrayOf(LoginModule::class), dependencies = arrayOf(AppComponent::class))
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)
}