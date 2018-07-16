package com.ly.genjidialogv2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ly.genjidialog.extensions.addShowDismissListener
import com.ly.genjidialog.extensions.convertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import com.ly.genjidialog.extensions.onKeyListenerFun
import com.ly.genjidialog.other.DialogGravity
import kotlinx.android.synthetic.main.aaa.view.*
import kotlinx.android.synthetic.main.activity_loading.*


class MainActivity : AppCompatActivity() {

    var clickTime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_loading)
        /* if (Build.VERSION.SDK_INT >= 21) {
             val decorView = window.decorView
             val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
             window.statusBarColor = Color.TRANSPARENT
             decorView.systemUiVisibility = option
             (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0).fitsSystemWindows = true
             //getWindow().setNavigationBarColor(Color.TRANSPARENT);
         } else if (Build.VERSION.SDK_INT >= 19) {
             (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0).fitsSystemWindows = false
             window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
         }*/
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        //******************activity_main的dialog代码*******************
        /* val genji = newGenjiDialog {
             layoutId = R.layout.aaa
             width = 329
             height = 252
             convertListenerFun { holder, dialog ->

             }
             addShowDismissListener("aaa") {
                 onDialogShow {

                 }
                 onDialogDismiss {

                 }
             }
             onKeyListenerFun { dialog, keyCode, event ->
                 return@onKeyListenerFun false
             }
         }.showOnWindow(supportFragmentManager)
         ltBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.LEFT_TOP, R.style.LeftTransAlphaADAnimation)
         }
         ctBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.CENTER_TOP, R.style.TopTransAlphaADAnimation)
         }
         rtBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.RIGHT_TOP, R.style.RightTransAlphaADAnimation)
         }
         lcBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.LEFT_CENTER, R.style.LeftTransAlphaADAnimation)
         }
         ccBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.CENTER_CENTER, R.style.AlphaEnterExitAnimation)
         }
         rcBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.RIGHT_CENTER, R.style.RightTransAlphaADAnimation)
         }
         lbBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.LEFT_BOTTOM, R.style.LeftTransAlphaADAnimation)
         }
         cbBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.CENTER_BOTTOM, R.style.BottomTransAlphaADAnimation)
         }
         rbBtn.setOnClickListener {
             genji.showOnWindow(supportFragmentManager, DialogGravity.RIGHT_BOTTOM, R.style.RightTransAlphaADAnimation)
         }
         needHealing.setOnClickListener {
             if (clickTime == 9) {
                 clickTime = 0
             }
             when (clickTime) {
                 0 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.LEFT_TOP, R.style.ScaleOverShootEnterExitAnimationX100Y100)
                 1 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.CENTER_TOP, R.style.ScaleOverShootEnterExitAnimationX50Y100)
                 2 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.RIGHT_TOP, R.style.ScaleOverShootEnterExitAnimationX0Y100)
                 3 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.LEFT_CENTER, R.style.ScaleOverShootEnterExitAnimationX100Y50)
                 4 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.CENTER_CENTER, R.style.ScaleOverShootEnterExitAnimationX50Y50)
                 5 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.RIGHT_CENTER, R.style.ScaleOverShootEnterExitAnimationX0Y50)
                 6 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.LEFT_BOTTOM, R.style.ScaleOverShootEnterExitAnimationX100Y0)
                 7 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.CENTER_BOTTOM, R.style.ScaleOverShootEnterExitAnimationX50Y0)
                 8 -> genji.showOnView(supportFragmentManager, needHealing, DialogGravity.RIGHT_BOTTOM, R.style.ScaleOverShootEnterExitAnimationX0Y0)
             }
             clickTime++
         }*/

        //********************activity_loading的dialog代码
        showLoading.setOnClickListener {
            newGenjiDialog {
                //设置布局
                layoutId = R.layout.aaa
                //设置宽度
                width = dp2px(100f)
                //设置高度
                height = dp2px(100f)
                //当时showOnWindow时设置显示位置
                //gravity = DialogGravity.RIGHT_TOP
                //处理事件/数据绑定
                convertListenerFun { view, holder, dialog ->
                    view.image.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                //添加show/dismiss时的监听事件
                addShowDismissListener("eventKey") {
                    onDialogShow {
                        Toast.makeText(this@MainActivity, "show", Toast.LENGTH_SHORT).show()
                    }
                    onDialogDismiss {
                        Toast.makeText(this@MainActivity, "dismiss", Toast.LENGTH_SHORT).show()
                    }
                }
                //监听按键
                onKeyListenerFun { dialog, keyCode, event ->
                    return@onKeyListenerFun false
                }
                //有遮罩的滑出位置
                //slideGravity = Gravity.TOP
                //阴影透明度
                dimAmount = 0.3f
                //动画
                animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y0
                //相对view的偏移
                offsetX = -showLoading.width / 2
                offsetY = -showLoading.height / 2
                //相对View的位置
                gravityAsView = DialogGravity.RIGHT_BOTTOM
                //showOnWindow的偏移
                //verticalMargin = dp2px(100f).toFloat()
                //horizontalMargin = dp2px(100f).toFloat()
                //isFullHorizontal 是否横向占满
                //isFullVertical 是否纵向占满 该纵向占满并非全屏，纵向占满会自动扣掉状态栏的高度
                //isFullVerticalOverStatusBar 该纵向占满全屏不会扣掉状态栏高度
                //touchCancel 是否点击屏幕区域取消（不包含返回按钮）
                //outCancel 是否点击外部取消 需要和touchCancel = false 一起使用
            }.showOnView(supportFragmentManager, showLoading)
        }
    }

    fun dp2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
