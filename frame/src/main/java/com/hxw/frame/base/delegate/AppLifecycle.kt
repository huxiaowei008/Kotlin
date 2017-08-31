package com.hxw.frame.base.delegate

import android.app.Application
import android.content.Context

/**
 * application的生命周期
 * Created by hxw on 2017/8/28.
 */
interface AppLifecycle {
    fun attachBaseContext(base:Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}