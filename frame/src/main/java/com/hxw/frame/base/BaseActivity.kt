package com.hxw.frame.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.di.FrameComponent
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Activity基类
 * @author hxw
 * @date 2017/8/29
 */
abstract class BaseActivity : AppCompatActivity(), IActivity, LifecycleProvider<ActivityEvent> {
    protected val TAG: String = this.javaClass.simpleName
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        componentInject(AppDelegate.FRAME_COMPONENT)
        init(savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

    override fun useFragment(): Boolean = false

    override fun componentInject(frameComponent: FrameComponent) {

    }
}