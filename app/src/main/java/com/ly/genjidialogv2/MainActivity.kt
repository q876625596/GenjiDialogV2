package com.ly.genjidialogv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.extensions.*
import com.ly.genjidialog.other.DialogGravity
import com.ly.genjidialogv2.databinding.AaaBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var testDialog: GenjiDialog? = null
    var ttt = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ttt.value = "hello"
        onWindowBtn.setOnClickListener {
            startActivity(Intent(this, DialogOnWindowActivity::class.java))
        }
        onViewBtn.setOnClickListener {
            startActivity(Intent(this, DialogOnViewActivity::class.java))
        }
        maskSlideBtn.setOnClickListener {
            ttt.value = "qwer"
            //startActivity(Intent(this, SlideWindowActivity::class.java))
        }
        textTest.setOnClickListener {
            Log.e("main", "aaaa")
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
            testDialog = newGenjiDialog { genjiDialog ->
                //layoutId = R.layout.aaa
                gravity = DialogGravity.CENTER_CENTER
                animStyle = R.style.BottomTransAlphaADAnimation
                unLeak = true
                bindingListenerFun { container, dialog ->
                    Log.e("main", "bind")
                    return@bindingListenerFun DataBindingUtil.inflate<AaaBinding>(inflater, R.layout.aaa, container, false).apply {
                        this.lifecycleOwner = dialog
                        this.act = this@MainActivity
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
                    dialogBinding.image.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                addShowDismissListener("aa") {
                    onDialogShow {
                        Log.e("main", "show")
                    }
                    onDialogDismiss {
                        Log.e("main", "ppppp")
                    }
                }
                onKeyListenerForOptions { keyCode, event ->
                    Log.e("main", keyCode.toString())
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        genjiDialog.dismiss()
                        return@onKeyListenerForOptions true
                    }
                    return@onKeyListenerForOptions false
                }

            }.showOnWindow(supportFragmentManager)
        }
    }
}