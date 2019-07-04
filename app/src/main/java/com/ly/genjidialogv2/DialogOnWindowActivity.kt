package com.ly.genjidialogv2

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.ly.genjidialog.extensions.convertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import com.ly.genjidialog.other.DialogGravity
import kotlinx.android.synthetic.main.activity_on_window.*

class DialogOnWindowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_window)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            val vis = decorView.systemUiVisibility
            decorView.systemUiVisibility = if (true) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
        /*******************在屏幕上显示dialog********************/
        val genji = newGenjiDialog { genjiDialog ->
            layoutId = R.layout.aaa
            width = 329
            height = 252
            unLeak = true
            convertListenerFun { holder, dialog ->
                holder.setOnClickListener(R.id.image) {
                    Toast.makeText(this@DialogOnWindowActivity, "I need healing", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ltBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.LEFT_TOP, R.style.LeftTransAlphaADAnimation)
        }
        ctBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.CENTER_TOP, R.style.TopTransAlphaADAnimation)
        }
        rtBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.RIGHT_TOP, R.style.RightTransAlphaADAnimation)
        }
        lcBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.LEFT_CENTER, R.style.LeftTransAlphaADAnimation)
        }
        ccBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.CENTER_CENTER, R.style.AlphaEnterExitAnimation)
        }
        rcBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.RIGHT_CENTER, R.style.RightTransAlphaADAnimation)
        }
        lbBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.LEFT_BOTTOM, R.style.LeftTransAlphaADAnimation)
        }
        cbBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.CENTER_BOTTOM, R.style.BottomTransAlphaADAnimation)
        }
        rbBtn.setOnClickListener {
            genji.showOnWindow(supportFragmentManager, DialogGravity.RIGHT_BOTTOM, R.style.RightTransAlphaADAnimation)
        }

    }
}