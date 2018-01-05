package com.modoutech.kotlin.mvp.login

import android.graphics.Color
import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import com.hxw.frame.base.DaggerActivity
import com.hxw.frame.imageloader.ImageLoader
import com.hxw.frame.utils.SpanUtils
import com.hxw.frame.utils.StringUtils
import com.jakewharton.rxbinding2.view.RxView
import com.modoutech.kotlin.R
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * @author hxw
 * @date 2017/9/19
 */
class LoginActivity : DaggerActivity(), LoginContract.View {

    @Inject lateinit var mPresenter: LoginContract.Presenter
    @Inject lateinit var imageLoader: ImageLoader

    override fun getLayoutId(): Int = R.layout.activity_login


    override fun init(savedInstanceState: Bundle?) {
        cb_keep.buttonDrawable = AppCompatResources.getDrawable(this, R.drawable.check_box_selector)
//        btn_login.setOnClickListener { mPresenter.login() }
        imageLoader.displayRes(img_logo, R.mipmap.ic_launcher)
        RxView.clicks(btn_login)
                .bindToLifecycle(this)//rxlifecycle的使用
                .subscribe {
                    mPresenter.login()
                }
        val map = StringUtils.urlRequestFormat("http://172.16.8.94:8080/GetNearByArea?token=195184551&lat=27.974277&lon=120.7336")
        tv_head.text = SpanUtils.newString("智能")
                .setForegroundColor(Color.YELLOW)
                .append("停车管理")
                .setForegroundColor(Color.RED)
                .setAbsoluteSize(10)
                .append("系统")
                .setBackgroundColor(Color.GRAY)
                .create()
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