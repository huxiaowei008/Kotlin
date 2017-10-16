package com.modoutech.kotlin.mvp.login

import com.hxw.frame.mvp.IPresenter
import com.hxw.frame.mvp.IView

/**
 * @author hxw
 * @date 2017/9/19
 */
interface LoginContract {
    interface View : IView<Presenter> {
        fun launchActivity()
    }

    interface Presenter : IPresenter<View> {
        fun login()
    }
}