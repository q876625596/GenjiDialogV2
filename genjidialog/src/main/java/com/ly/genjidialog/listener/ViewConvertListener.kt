package com.ly.genjidialog.listener

import android.os.Parcel
import android.os.Parcelable
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.other.ViewHolder

abstract class ViewConvertListener : Parcelable {

    abstract fun convertView(holder: ViewHolder, dialog: GenjiDialog)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    constructor() {}

    protected constructor(source: Parcel) {}

    companion object {
        val CREATOR: Parcelable.Creator<ViewConvertListener> = object : Parcelable.Creator<ViewConvertListener> {
            override fun createFromParcel(source: Parcel): ViewConvertListener {
                return object : ViewConvertListener(source) {
                    override fun convertView(holder: ViewHolder, dialog: GenjiDialog) {

                    }
                }
            }

            override fun newArray(size: Int): Array<ViewConvertListener?> {
                return arrayOfNulls(size)
            }
        }
    }
}
