package com.hxw.frame.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * activity基类
 * Created by hxw on 2017/8/29.
 */
abstract class BaseActivity : RxAppCompatActivity(), IActivity {
    protected val TAG = this.javaClass.simpleName!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }

        init(savedInstanceState)
    }

    override fun useFragment(): Boolean = false

}