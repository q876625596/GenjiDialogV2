package com.ly.genjidialog.listener

import android.os.Parcel
import android.os.Parcelable

class DialogShowOrDismissListener : Parcelable {

    //是否在dialog显示的时候执行onDialogShow()方法
    var enableExecuteShowListener = true

    //是否在dialog关闭的时候执行onDialogDismiss()方法
    var enableExecuteDismissListener = true

    private var dialogShowFun: (() -> Unit)? = null

    private var dialogDismissFun: (() -> Unit)? = null

    fun onDialogShow(listener: () -> Unit) {
        dialogShowFun = listener
    }

    fun onDialogDismiss(listener: () -> Unit) {
        dialogDismissFun = listener
    }

    fun onDialogShow() {
        dialogShowFun?.invoke()
    }

    fun onDialogDismiss() {
        dialogDismissFun?.invoke()
    }

    constructor()

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DialogShowOrDismissListener> = object : Parcelable.Creator<DialogShowOrDismissListener> {
            override fun createFromParcel(source: Parcel): DialogShowOrDismissListener = DialogShowOrDismissListener(source)
            override fun newArray(size: Int): Array<DialogShowOrDismissListener?> = arrayOfNulls(size)
        }
    }
}