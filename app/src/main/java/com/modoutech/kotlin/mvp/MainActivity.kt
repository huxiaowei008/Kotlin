package com.modoutech.kotlin.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.modoutech.kotlin.R

/**
 * @author hxw
 * @date 2017/9/19
 */
class MainActivity : AppCompatActivity() {
    //    override fun getLayoutId(): Int = R.layout.activity_main
//
//
//    override fun init(savedInstanceState: Bundle?) {
//        txt_hello.text = "是对方空间"
//        txt_hello.setTextColor(Color.BLUE)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
