package com.hxw.frame.integration.lifecycle

import android.support.annotation.NonNull
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.Subject

/**
 * 让 {@link Activity} 实现此接口,即可正常使用 {@link RxLifecycle}
 * @author hxw
 * @date 2017/9/22
 */
interface ActivityLifecycleable : LifecycleProvider<ActivityEvent> {

    @NonNull
    fun lifecycleSubject(): Subject<ActivityEvent>
}