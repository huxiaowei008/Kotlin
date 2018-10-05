package com.modoutech.kotlin.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

import com.modoutech.kotlin.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author hxw
 * @date 2017/9/19
 */
@Route(path = "/app/MainActivity")
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

        btn_a.setOnClickListener {
            ARouter.getInstance().build("/moduleA/ModuleAActivity").navigation()
        }
    }
}
