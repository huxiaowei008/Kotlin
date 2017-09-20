package com.modoutech.kotlin.di

import com.hxw.frame.di.FrameComponent
import com.hxw.frame.di.scope.AppScope
import com.modoutech.kotlin.base.MyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by hxw on 2017/9/20.
 */
@AppScope
@Component(dependencies = arrayOf(FrameComponent::class),
        modules = arrayOf(ActivityBindingModule::class,
                AndroidSupportInjectionModule::class))
interface AppComponent : AndroidInjector<MyApplication> {
}