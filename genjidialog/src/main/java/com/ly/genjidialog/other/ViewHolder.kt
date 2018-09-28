package com.ly.genjidialog.other

import android.support.annotation.IdRes
import android.util.SparseArray
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class ViewHolder(var rootView: View, private var views: SparseArray<View> = SparseArray()) {

    fun <T : View> getView(@IdRes viewId: Int): T? {
        views.get(viewId)?.let {
            return it as T
        }
        rootView.findViewById<T>(viewId)?.let {
            views.put(viewId, it)
            return it
        }
        return null
    }

    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        getView<TextView>(viewId).let {
            //使用!!是为了更好的排查在编写代码时的错误
            it!!.text = text
        }
        return this
    }

    fun setText(viewId: Int, textId: Int): ViewHolder {
        getView<TextView>(viewId).let {
            it!!.setText(textId)
        }
        return this
    }

    fun setVisible(viewId: Int, visible: Boolean): ViewHolder {
        getView<View>(viewId).let {
            it!!.visibility = if (visible) View.VISIBLE else View.GONE
        }
        return this
    }

    fun setInVisible(viewId: Int, visible: Boolean): ViewHolder {
        getView<View>(viewId).let {
            it!!.visibility = if (visible) View.INVISIBLE else View.GONE
        }
        return this
    }

    fun setTextColor(viewId: Int, colorId: Int): ViewHolder {
        getView<TextView>(viewId).let {
            it!!.setTextColor(colorId)
        }
        return this
    }

    fun setImageResource(viewId: Int, imageRes: Int): ViewHolder {
        getView<ImageView>(viewId).let {
            it!!.setImageResource(imageRes)
        }
        return this
    }

    fun setChecked(viewId: Int, isChecked: Boolean): ViewHolder {
        getView<CheckBox>(viewId).let {
            it!!.isChecked = isChecked
        }
        return this
    }

    fun setOnClickListener(viewId: Int, clickListener: (v: View) -> Unit): ViewHolder {
        getView<View>(viewId).let {
            it!!.setOnClickListener(clickListener)
        }
        return this
    }

    fun setOnLongClickListener(viewId: Int, clickListener: (v: View) -> Boolean): ViewHolder {
        getView<View>(viewId).let {
            it!!.setOnLongClickListener(clickListener)
        }
        return this
    }

    fun setBackgroundResource(viewId: Int, resId: Int): ViewHolder {
        getView<View>(viewId).let {
            it!!.setBackgroundResource(resId)
        }
        return this
    }

    fun setBackgroundColor(viewId: Int, colorId: Int): ViewHolder {
        getView<View>(viewId).let {
            it!!.setBackgroundColor(colorId)
        }
        return this
    }
}