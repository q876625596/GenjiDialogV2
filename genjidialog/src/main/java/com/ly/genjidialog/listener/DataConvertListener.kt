package com.ly.genjidialog.listener

import android.os.Parcel
import android.os.Parcelable
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.other.ViewHolder

abstract class DataConvertListener : Parcelable {

    abstract fun convertView(dialogBinding: Any, dialog: GenjiDialog)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    constructor() {}

    protected constructor(source: Parcel) {}

    companion object {
        val CREATOR: Parcelable.Creator<DataConvertListener> = object : Parcelable.Creator<DataConvertListener> {
            override fun createFromParcel(source: Parcel): DataConvertListener {
                return object : DataConvertListener(source) {
                    override fun convertView(dialogBinding: Any, dialog: GenjiDialog) {

                    }
                }
            }

            override fun newArray(size: Int): Array<DataConvertListener?> {
                return arrayOfNulls(size)
            }
        }
    }
}
