package com.hxw.frame.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.di.FrameComponent
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Fragment基类
 * @author hxw
 * @date 2017/8/30
 */
abstract class BaseFragment : Fragment(), IFragment, LifecycleProvider<FragmentEvent> {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(getLayoutId(), container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        componentInject(AppDelegate.FRAME_COMPONENT)
        init(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }

    override fun componentInject(frameComponent: FrameComponent) {

    }
}