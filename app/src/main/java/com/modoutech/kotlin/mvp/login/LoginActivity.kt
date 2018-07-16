package com.modoutech.kotlin.mvp.login

import android.support.v7.app.AppCompatActivity

/**
 * @author hxw
 * @date 2017/9/19
 */
class LoginActivity : AppCompatActivity(){


//    lateinit var mPresenter: LoginContract.Presenter
//
//    lateinit var imageLoader: ImageLoader
//
//    override fun getLayoutId(): Int = R.layout.activity_login
//
//
//    override fun init(savedInstanceState: Bundle?) {
//        cb_keep.buttonDrawable = AppCompatResources.getDrawable(this, R.drawable.check_box_selector)
////        btn_login.setOnClickListener { mPresenter.login() }
//        imageLoader.displayRes(img_logo, R.mipmap.ic_launcher)
//
//        cb_keep.setOnCheckedChangeListener { buttonView, isChecked ->
//            setStatusBarDarkMode(isChecked)
//        }
//        RxView.clicks(btn_login)
//                .bindToLifecycle(this)//rxlifecycle的使用
//                .subscribe {
//                    mPresenter.login()
//
//                }
//        val map = "http://172.16.8.94:8080/GetNearByArea?token=195184551&lat=27.974277&lon=120.7336".urlRequestFormat()
//        tv_head.text = SpanUtils.newString("智能")
//                .setForegroundColor(Color.YELLOW)
//                .append("停车管理")
//                .setForegroundColor(Color.RED)
//                .setAbsoluteSize(10)
//                .append("系统")
//                .setBackgroundColor(Color.GRAY)
//                .create()
//    }
//
//    override fun showMessage(message: String) {
//        val t = et_username.text.toString()
//        UIUtils.toast("${t.isZh()}")
//
//    }
//
//    override fun launchActivity() {
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mPresenter.injectView(this)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mPresenter.dropView()
//    }
}