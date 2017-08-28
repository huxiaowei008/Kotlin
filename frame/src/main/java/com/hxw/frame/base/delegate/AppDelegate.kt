package com.hxw.frame.base.delegate

import android.app.Application
import android.content.Context
import com.hxw.frame.base.DelegatesExt
import com.hxw.frame.di.AppComponent
import com.hxw.frame.di.DaggerAppComponent
import com.hxw.frame.di.module.AppModule
import com.hxw.frame.di.module.ClientModule

/**
 * Created by hxw on 2017/8/28.
 */
class AppDelegate : AppLifecycle {

    companion object {
        var appComponent: AppComponent by DelegatesExt.notNullSingleValue()
        var instance: Application by DelegatesExt.notNullSingleValue()
    }

    override fun attachBaseContext(base: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(application: Application) {
        instance = application
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(application))
                .clientModule(ClientModule())
                .build()
        appComponent.inject(this)
    }

    override fun onTerminate(application: Application) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}