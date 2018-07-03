package com.hebe.frameworklearn.kotlin

import android.content.Intent
import android.os.Bundle
import com.hebe.frameworklearn.R
import com.hebe.frameworklearn.base.BaseActivity
import com.hebe.frameworklearn.java.Test1Activity
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helloWorld.text = "MainActivity"
        helloWorld.setOnClickListener({
//            startActivity(Intent(this,Test1Activity::class.java))
            startActivity(Intent(this,Test1Activity::class.java))
        })
//        val a = 0;
//        val str1 = """$a hahahha"""
//        val str2 = "$a hahahha"
//        helloWorld.text = str1;
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
