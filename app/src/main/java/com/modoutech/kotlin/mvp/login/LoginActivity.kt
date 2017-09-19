package com.modoutech.kotlin.mvp.login

import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import com.hxw.frame.base.BaseActivity
import com.hxw.frame.di.AppComponent
import com.modoutech.kotlin.R
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by hxw on 2017/9/19.
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    @Inject lateinit var mPresenter: LoginContract.Presenter

    override fun getLayoutId(): Int = R.layout.activity_login


    override fun componentInject(appComponent: AppComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun init(savedInstanceState: Bundle?) {
        cb_keep.buttonDrawable = AppCompatResources.getDrawable(this, R.drawable.check_box_selector)
        btn_login.setOnClickListener { mPresenter.login() }
    }

    override fun showMessage(message: String) {

    }

    override fun launchActivity() {

    }

    override fun onResume() {
        super.onResume()
        mPresenter.injectView(this)
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}