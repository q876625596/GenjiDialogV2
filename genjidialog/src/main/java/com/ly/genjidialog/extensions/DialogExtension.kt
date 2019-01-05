package com.ly.genjidialog.extensions

import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.listener.DataConvertListener
import com.ly.genjidialog.listener.DialogShowOrDismissListener
import com.ly.genjidialog.listener.OnKeyListener
import com.ly.genjidialog.listener.ViewConvertListener
import com.ly.genjidialog.other.DialogOptions
import com.ly.genjidialog.other.ViewHolder

/**
 * 创建一个dialog
 * 你也可以继承GenjiDialog，实现更多功能，并通过扩展函数来简化创建过程
 */
inline fun newGenjiDialog(options: DialogOptions.(dialog: GenjiDialog) -> Unit): GenjiDialog {
    val genjiDialog = GenjiDialog()
    genjiDialog.getDialogOptions().options(genjiDialog)
    return genjiDialog
}

/**
 * 当需要通过继承GenjiDialog来分离Activity与dialog的代码时，
 * 可通过该扩展方法在子类中创建DialogOptions，可见AAA
 */
inline fun GenjiDialog.dialogOptionsFun(dialogOp: DialogOptions.() -> Unit): DialogOptions {
    val options = DialogOptions()
    options.dialogOp()
    setDialogOptions(options)
    return options
}

/**
 * 设置convertListener的扩展方法
 */
inline fun DialogOptions.convertListenerFun(crossinline listener: (holder: ViewHolder, dialog: GenjiDialog) -> Unit) {
    val viewConvertListener = object : ViewConvertListener() {
        override fun convertView(holder: ViewHolder, dialog: GenjiDialog) {
            listener.invoke(holder, dialog)
        }
    }
    convertListener = viewConvertListener
}

/**
 * 设置dataListener的扩展方法
 */
inline fun DialogOptions.dataConvertListenerFun(crossinline listener: (dialogBinding: Any, dialog: GenjiDialog) -> Unit) {
    val dataBindingConvertListener = object : DataConvertListener() {
        override fun convertView(dialogBinding: Any, dialog: GenjiDialog) {
            listener.invoke(dialogBinding, dialog)
        }
    }
    dataConvertListener = dataBindingConvertListener
}

/**
 * 设置dataBindingListener的扩展方法
 */
inline fun DialogOptions.bindingListenerFun(crossinline listener: (container: ViewGroup?,dialog:GenjiDialog) -> View) {
    val newBindingListener = { container: ViewGroup?,dialog:GenjiDialog ->
        listener.invoke(container,dialog)
    }
    bindingListener = newBindingListener
}


/**
 * 添加DialogShowOrDismissListener的扩展方法
 */
inline fun DialogOptions.addShowDismissListener(key: String, dialogInterface: DialogShowOrDismissListener.() -> Unit): DialogOptions {
    val dialogShowOrDismissListener = DialogShowOrDismissListener()
    dialogShowOrDismissListener.dialogInterface()
    showDismissMap[key] = dialogShowOrDismissListener
    return this
}

/**
 * 设置OnKeyListener的扩展方法
 */
inline fun DialogOptions.onKeyListenerForOptions(crossinline listener: (dialog: DialogInterface, keyCode: Int, event: KeyEvent) -> Boolean) {
    val onKey = object : OnKeyListener() {
        override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
            return listener.invoke(dialog, keyCode, event)
        }
    }
    onKeyListener = onKey
}

/**
 * 针对特殊动画需要调用genjidialog.dismiss()的方法
 */
inline fun GenjiDialog.onKeyListenerForDialog(crossinline listener: (genjidialog: GenjiDialog, dialogInterFace: DialogInterface, keyCode: Int, event: KeyEvent) -> Boolean): GenjiDialog {
    val onKey = object : OnKeyListener() {
        override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
            return listener.invoke(this@onKeyListenerForDialog, dialog, keyCode, event)
        }
    }
    getDialogOptions().onKeyListener = onKey
    return this
}

