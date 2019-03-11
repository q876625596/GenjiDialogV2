package com.ly.genjidialogv2

import android.graphics.RectF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ly.genjidialog.extensions.convertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import com.ly.genjidialog.mask.HighlightArea
import com.ly.genjidialog.mask.MaskView
import com.ly.genjidialog.other.DialogGravity
import kotlinx.android.synthetic.main.activity_mask.*

class MaskViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mask)
        btn.postDelayed({
            newGenjiDialog {
                layoutId = R.layout.dialog_mask
                dimAmount = 0f
                isFullHorizontal = true
                isFullVerticalOverStatusBar = true
                gravity = DialogGravity.CENTER_CENTER
                animStyle = R.style.AlphaEnterExitAnimation
                convertListenerFun { holder, dialog ->
                    holder.getView<MaskView>(R.id.maskView)?.apply {
                        this.highlightArea = HighlightArea(RectF(
                                this@MaskViewActivity.btn.left.toFloat(),
                                this@MaskViewActivity.btn.top.toFloat(),
                                this@MaskViewActivity.btn.right.toFloat(),
                                this@MaskViewActivity.btn.bottom.toFloat()))
                    }
                }
            }.showOnWindow(supportFragmentManager)
        }, 200L)
    }
}