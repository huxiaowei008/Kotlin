package com.modoutech.kotlin.mvp.login

import com.hxw.frame.di.scope.ActivityScope
import com.hxw.frame.http.AbstractErrorSubscriber
import com.hxw.frame.integration.RepositoryManager
import com.hxw.frame.utils.UIUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author hxw
 * @date 2017/9/19
 */
@ActivityScope
class LoginPresenter @Inject constructor(
        private val repositoryManager: RepositoryManager) : LoginContract.Presenter {

    var mView: LoginContract.View? = null

    override fun injectView(view: LoginContract.View) {
        mView = view
    }

    override fun login() {
        Observable.just(0)
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .subscribe(object : AbstractErrorSubscriber<Int>() {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: Int) {
                        UIUtils.toast("成功")
                    }
                })

    }

    override fun dropView() {
        mView = null
    }
}