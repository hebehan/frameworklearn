package com.hebe.frameworklearn.kotlin

import android.os.Bundle
import com.hebe.frameworklearn.R
import com.hebe.frameworklearn.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class Kotlin2Activity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helloWorld.text = "Kotlin2Activity"

    }
}