package com.hxw.modulea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main_a.*

@Route(path = "/moduleA/ModuleAActivity")
class ModuleAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_a)

        tv_a.setOnClickListener {   ARouter.getInstance().build("/app/MainActivity").navigation() }
    }
}
