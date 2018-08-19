package com.ly.genjidialogv2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onWindowBtn.setOnClickListener {
            startActivity(Intent(this, DialogOnWindowActivity::class.java))
        }
        onViewBtn.setOnClickListener {
            startActivity(Intent(this, DialogOnViewActivity::class.java))
        }
        maskSlideBtn.setOnClickListener {
            startActivity(Intent(this, SlideWindowActivity::class.java))
        }
        testBtn.setOnClickListener {
            val aaa = AAA()
            aaa.getDialogOptions().animStyle = R.style.ScaleADEnterExitAnimationX50Y50
            aaa.showOnWindow(supportFragmentManager)
        }
    }
}