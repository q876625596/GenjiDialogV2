package com.ly.genjidialog.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager

/**
 * 在view显示之前读取宽高的扩展方法
 */
fun View.unDisplayViewSize(): IntArray {
    val size = IntArray(2)
    val width = View.MeasureSpec.makeMeasureSpec(0,
            View.MeasureSpec.UNSPECIFIED)
    val height = View.MeasureSpec.makeMeasureSpec(0,
            View.MeasureSpec.UNSPECIFIED)
    measure(width, height)
    size[0] = measuredWidth
    size[1] = measuredHeight
    return size
}

/**
 * 获取屏幕宽度
 */
fun Resources.getScreenWidth(): Int {
    val displayMetrics = displayMetrics
    return displayMetrics.widthPixels
}

/**
 * 获取除了statusBar以外的屏幕高度
 */
fun Resources.getScreenHeight(): Int {
    val displayMetrics = displayMetrics
    return displayMetrics.heightPixels
}

/**
 * 获取整个屏幕高度的扩展方法
 */
fun AppCompatActivity.getScreenHeightOverStatusBar(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return resources.displayMetrics.heightPixels
    val point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}

fun Resources.dp2px(dpValue: Float): Int {
    val scale = displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 垃圾jitpack，居然找不到我的扩展函数，逼着我写Utils
 */
class UtilsExtension {
    companion object {

        fun dp2px(resources: Resources,dpValue: Float): Int {
            val scale = resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 在view显示之前读取宽高的扩展方法
         */
        fun unDisplayViewSize(view: View): IntArray {
            val size = IntArray(2)
            val width = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED)
            val height = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED)
            view.measure(width, height)
            size[0] = view.measuredWidth
            size[1] = view.measuredHeight
            return size
        }

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            return displayMetrics.widthPixels
        }

        /**
         * 获取除了statusBar以外的屏幕高度
         */
        fun getScreenHeight(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            return displayMetrics.heightPixels
        }

        /**
         * 获取整个屏幕高度的扩展方法
         */
        fun getScreenHeightOverStatusBar(mActivity: AppCompatActivity): Int {
            val wm = mActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    ?: return mActivity.resources.displayMetrics.heightPixels
            val point = Point()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.defaultDisplay.getRealSize(point)
            } else {
                wm.defaultDisplay.getSize(point)
            }
            return point.y
        }
    }
}
