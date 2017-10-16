package com.modoutech.kotlin.mvp

import android.graphics.Color
import android.os.Bundle
import com.hxw.frame.base.BaseActivity
import com.modoutech.kotlin.R
import kotlinx.android.synthetic.main.activity_main.*
/**
 * @author hxw
 * @date 2017/9/19
 */
class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main


    override fun init(savedInstanceState: Bundle?) {
        txt_hello.text = "是对方空间"
        txt_hello.setTextColor(Color.BLUE)
    }

}
