package com.modoutech.kotlin.mvp.login

import com.hxw.frame.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

/**
 * Created by hxw on 2017/9/19.
 */
@Module
abstract class LoginModule {
    @ActivityScope
    @Binds
    abstract fun loginPresenter(loginPresenter: LoginPresenter): LoginContract.Presenter
}