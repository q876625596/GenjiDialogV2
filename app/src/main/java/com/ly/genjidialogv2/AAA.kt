package com.ly.genjidialogv2

import android.widget.Toast
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.extensions.addShowDismissListener
import com.ly.genjidialog.extensions.dialogOptionsFun
import com.ly.genjidialog.other.DialogOptions

class AAA : GenjiDialog() {
    override var dialogOptions: DialogOptions = dialogOptionsFun {
        layoutId = R.layout.aaa
        isFullHorizontal = true
        isFullVerticalOverStatusBar = true
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
