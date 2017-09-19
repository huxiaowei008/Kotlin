package com.modoutech.kotlin.mvp.login

import com.hxw.frame.di.scope.ActivityScope
import com.hxw.frame.integration.RepositoryManager
import com.hxw.frame.utils.UIUtils
import javax.inject.Inject

/**
 * Created by hxw on 2017/9/19.
 */
@ActivityScope
class LoginPresenter @Inject constructor(
        private val repositoryManager: RepositoryManager) : LoginContract.Presenter {

    var mView: LoginContract.View? = null

    override fun injectView(view: LoginContract.View) {
        mView = view
    }

    override fun login() {
        UIUtils.toast("成功")
    }

    override fun onDestroy() {
        mView = null
    }
}