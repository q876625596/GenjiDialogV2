package com.ly.genjidialogv2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ly.genjidialog.extensions.addShowDismissListener
import com.ly.genjidialog.extensions.convertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import com.ly.genjidialog.extensions.onKeyListenerForOptions
import com.ly.genjidialog.other.DialogGravity
import kotlinx.android.synthetic.main.activity_main.*

class DialogOnWindowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*******************普通动画示例 activity_main布局********************/
        val genji = newGenjiDialog {
            layoutId = R.layout.aaa
            width = 329
            height = 252
            convertListenerFun { holder, dialog ->

            }
            addShowDismissListener("aaa") {
                onDialogShow {

                }
                onDialogDismiss {

                }
            }
            onKeyListenerForOptions { dialog, keyCode, event ->
                return@onKeyListenerForOptions false
            }
        }.showOnWindow(supportFragmentManager)

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