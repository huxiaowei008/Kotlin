package com.hxw.frame.base

import android.os.Bundle
import com.hxw.frame.base.delegate.AppDelegate
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by hxw on 2017/8/29.
 */
abstract class BaseActivity : RxAppCompatActivity(), IActivity {
    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        componentInject(AppDelegate.appComponent)
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }

        init(savedInstanceState)
    }

    override fun useFragment(): Boolean = false

}