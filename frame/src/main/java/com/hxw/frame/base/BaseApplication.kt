package com.hxw.frame.base

import android.app.Application
import com.hxw.frame.di.AppComponent
import com.hxw.frame.utils.UIUtils

/**
 * Created by hxw on 2017/8/19.
 */
class BaseApplication : Application(), App {


    override fun onCreate() {
        super.onCreate()
        UIUtils.initSingle(this)
    }


    override fun getAppComponent(): AppComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}