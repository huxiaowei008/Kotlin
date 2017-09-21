package com.modoutech.kotlin.mvp.login

import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import com.hxw.frame.base.DaggerActivity
import com.hxw.frame.imageloader.ImageLoader
import com.modoutech.kotlin.R
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by hxw on 2017/9/19.
 */
class LoginActivity : DaggerActivity(), LoginContract.View {

    @Inject lateinit var mPresenter: LoginContract.Presenter
    @Inject lateinit var imageLoader: ImageLoader

    override fun getLayoutId(): Int = R.layout.activity_login


    override fun init(savedInstanceState: Bundle?) {
        cb_keep.buttonDrawable = AppCompatResources.getDrawable(this, R.drawable.check_box_selector)
        btn_login.setOnClickListener { mPresenter.login() }
        imageLoader.displayRes(img_logo,R.mipmap.ic_launcher)
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