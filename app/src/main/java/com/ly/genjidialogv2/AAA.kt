package com.ly.genjidialogv2

import android.widget.Toast
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.extensions.addShowDismissListener
import com.ly.genjidialog.other.DialogOptions

class AAA : GenjiDialog() {

    override fun extendsOptions(): DialogOptions {
        /*继承GenjiDialog的时候一定要使用getDialogOptions()，用原有的进行修改*/
        return getDialogOptions().apply {
            layoutId = R.layout.aaa
            //isFullHorizontal = true
           /* animStyle = if ((getMyActivity() as MainActivity).isScale) R.style.ScaleADEnterExitAnimationX50Y50
            else R.style.AlphaEnterExitAnimation*/
            //isFullVerticalOverStatusBar = true
            addShowDismissListener("aaa") {
                onDialogShow {
                    Toast.makeText(getMyActivity(), "aaa", Toast.LENGTH_SHORT).show()
                }
                onDialogDismiss {
                    Toast.makeText(getMyActivity(), "bbb", Toast.LENGTH_SHORT).show()
                }
            }
            /*onKeyListenerFun { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return@onKeyListenerFun true
                }
                return@onKeyListenerFun false
            }*/
        }
    }
}
