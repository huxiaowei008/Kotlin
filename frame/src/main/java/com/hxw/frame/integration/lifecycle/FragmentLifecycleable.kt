package com.hxw.frame.integration.lifecycle

import android.support.annotation.NonNull
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.Subject

/**
 * 让 {@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 * Created by hxw on 2017/9/22.
 */
interface FragmentLifecycleable : LifecycleProvider<FragmentEvent> {

    @NonNull
    fun lifecycleSubject(): Subject<FragmentEvent>
}