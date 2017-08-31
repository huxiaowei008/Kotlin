package com.hxw.frame.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hxw.frame.base.delegate.AppDelegate
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * fragment基类
 * Created by hxw on 2017/8/30.
 */
abstract class BaseFragment :RxFragment(),IFragment{
    protected val TAG=this.javaClass.simpleName!!

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(getLayoutId(),container,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        componentInject(AppDelegate.appComponent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init(savedInstanceState)
    }
}