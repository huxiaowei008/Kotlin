package com.hxw.frame.di.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * fragment作用域
 * Created by hxw on 2017/8/30.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class FragmentScope