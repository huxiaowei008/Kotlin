package com.hxw.frame.base

import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hxw.frame.integration.lifecycle.FragmentLifecycleable
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * fragment基类
 * Created by hxw on 2017/8/30.
 */
abstract class BaseFragment : Fragment(), IFragment, FragmentLifecycleable {
    protected val TAG = this.javaClass.simpleName!!
    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    @CheckResult
    override final fun lifecycle(): Observable<FragmentEvent> = lifecycleSubject.hide()

    @CheckResult
    override final fun <T : Any?> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> =
            RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    @CheckResult
    override final fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> =
            RxLifecycleAndroid.bindFragment(lifecycleSubject)

    @CheckResult
    override fun lifecycleSubject(): Subject<FragmentEvent> = lifecycleSubject

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(getLayoutId(), container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init(savedInstanceState)
    }
}