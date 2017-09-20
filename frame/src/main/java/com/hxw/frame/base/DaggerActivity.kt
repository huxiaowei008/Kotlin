package com.hxw.frame.base

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dagger.internal.Beta
import javax.inject.Inject

/**
 * 使用注入的activity基类
 * Created by hxw on 2017/9/20.
 */
@Beta
abstract class DaggerActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
}