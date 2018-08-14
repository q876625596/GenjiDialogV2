package com.ly.genjidialogv2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ly.genjidialog.extensions.newGenjiDialog
import kotlinx.android.synthetic.main.activity_guide.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guide1.setOnClickListener {
            newGenjiDialog {
                layoutId
            }
        }
    }
}