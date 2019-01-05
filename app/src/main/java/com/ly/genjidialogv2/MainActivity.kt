package com.ly.genjidialogv2

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.extensions.addShowDismissListener
import com.ly.genjidialog.extensions.bindingListenerFun
import com.ly.genjidialog.extensions.dataConvertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import com.ly.genjidialog.other.DialogGravity
import com.ly.genjidialogv2.databinding.AaaBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var testDialog: GenjiDialog? = null

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
            /* val aaa = AAA()
             aaa.getDialogOptions().animStyle = R.style.ScaleADEnterExitAnimationX50Y50
             aaa.showOnWindow(supportFragmentManager)*/
            if (testDialog != null) {
                testDialog?.showOnWindow(supportFragmentManager)
                return@setOnClickListener
            }
            val inflater = LayoutInflater.from(this)
            testDialog = newGenjiDialog {
                //layoutId = R.layout.aaa
                gravity = DialogGravity.CENTER_CENTER
                animStyle = R.style.BottomTransAlphaADAnimation
                bindingListenerFun { container, dialog ->
                    return@bindingListenerFun DataBindingUtil.inflate<AaaBinding>(inflater, R.layout.aaa, container, false).apply {
                        this.textStr = "hello"
                        //setVariable(BR.textStr,"hello")
                        dialog.dialogBinding = this

                    }.root
                }
                /*outCancel = false
                touchCancel = false*/
                /*convertListenerFun { holder, dialog ->
                    Log.e("main", "aaa:${holder.getView<ImageView>(R.id.image)!!.height}")
                }*/
                dataConvertListenerFun { dialogBinding, dialog ->
                    dialogBinding as AaaBinding
                }
                addShowDismissListener("aa") {
                    onDialogDismiss {
                        Log.e("main", "ppppp")
                    }
                }

            }.showOnWindow(supportFragmentManager)
        }
    }
}