package com.ly.genjidialog.other

import android.view.Gravity

enum class DialogGravity private constructor(val index: Int) {
    LEFT_TOP(Gravity.START or Gravity.TOP),
    CENTER_TOP(Gravity.CENTER_HORIZONTAL or Gravity.TOP),
    RIGHT_TOP(Gravity.END or Gravity.TOP),
    LEFT_CENTER(Gravity.START or Gravity.CENTER_VERTICAL),
    CENTER_CENTER(Gravity.CENTER),
    RIGHT_CENTER(Gravity.END or Gravity.CENTER_VERTICAL),
    LEFT_BOTTOM(Gravity.START or Gravity.BOTTOM),
    CENTER_BOTTOM(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM),
    RIGHT_BOTTOM(Gravity.END or Gravity.BOTTOM)
}
