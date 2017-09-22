package com.hxw.frame.base

import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.hxw.frame.integration.lifecycle.ActivityLifecycleable
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * activity基类
 * Created by hxw on 2017/8/29.
 */
abstract class BaseActivity : AppCompatActivity(), IActivity, ActivityLifecycleable {
    protected val TAG = this.javaClass.simpleName!!
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    @CheckResult
    override final fun lifecycle(): Observable<ActivityEvent> = lifecycleSubject.hide()

    @NonNull
    @CheckResult
    override final fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> =
            RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    @NonNull
    @CheckResult
    override final fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> =
            RxLifecycleAndroid.bindActivity(lifecycleSubject)

    @CheckResult
    override final fun lifecycleSubject(): Subject<ActivityEvent> = lifecycleSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }

        init(savedInstanceState)
    }

    override fun useFragment(): Boolean = false

}