package com.ly.genjidialogv2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ly.genjidialog.extensions.addShowDismissListener
import com.ly.genjidialog.extensions.convertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import com.ly.genjidialog.extensions.onKeyListenerForOptions
import com.ly.genjidialog.other.DialogGravity
import kotlinx.android.synthetic.main.activity_main.*

class DialogOnViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* if (Build.VERSION.SDK_INT >= 21) {
                   val decorView = window.decorView
                   val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                   window.statusBarColor = Color.TRANSPARENT
                   decorView.systemUiVisibility = option
                   (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0).fitsSystemWindows = true
                   //getWindow().setNavigationBarColor(Color.TRANSPARENT);
               } else if (Build.VERSION.SDK_INT >= 19) {
                   (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0).fitsSystemWindows = false
                   window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
               }*/
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

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
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.LEFT_TOP, R.style.ScaleOverShootEnterExitAnimationX100Y100)
        }
        ctBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.CENTER_TOP, R.style.ScaleOverShootEnterExitAnimationX50Y100)
        }
        rtBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.RIGHT_TOP, R.style.ScaleOverShootEnterExitAnimationX0Y100)
        }
        lcBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.LEFT_CENTER, R.style.ScaleOverShootEnterExitAnimationX100Y50)
        }
        ccBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.CENTER_CENTER, R.style.ScaleOverShootEnterExitAnimationX50Y50)
        }
        rcBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.RIGHT_CENTER, R.style.ScaleOverShootEnterExitAnimationX0Y50)
        }
        lbBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.LEFT_BOTTOM, R.style.ScaleOverShootEnterExitAnimationX100Y0)
        }
        cbBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.CENTER_BOTTOM, R.style.ScaleOverShootEnterExitAnimationX50Y0)
        }
        rbBtn.setOnClickListener {
            genji.showOnView(supportFragmentManager, needHealing, DialogGravity.RIGHT_BOTTOM, R.style.ScaleOverShootEnterExitAnimationX0Y0)
        }

    }
}