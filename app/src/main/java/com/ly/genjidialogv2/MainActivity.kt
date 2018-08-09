package com.ly.genjidialogv2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ly.genjidialog.extensions.*
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


        /*******************普通动画示例 activity_main布局********************/
        /*   val genji = newGenjiDialog {
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
               onKeyListenerForOptions { dialog, keyCode, event ->
                   return@onKeyListenerForOptions false
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

        /**********特殊动画的示例  activity_loading布局*************/
        showLoading.setOnClickListener {
            val aaa = newGenjiDialog {
                //设置布局
                layoutId = R.layout.slide_test
                //设置宽度
                //width = dp2px(100f)
                //设置高度
                //height = dp2px(100f)
                isFullHorizontal = true
                isFullVerticalOverStatusBar = true
                //当时showOnWindow时设置显示位置
                //gravity = DialogGravity.RIGHT_TOP
                //处理事件/数据绑定
                convertListenerFun { holder, dialog ->
                    holder.setOnClickListener(R.id.touchView) {
                        if (canClick) {
                            dialog.dismiss()
                        }
                    }.setOnClickListener(R.id.topTouchView) {
                        if (canClick) {
                            dialog.dismiss()
                        }
                    }
                }
                setOnEnterAnimator { rootView ->
                    //在此处设置进入动画
                    AnimatorSet().apply {
                        duration = 1000L
                        val targetView = rootView.findViewById<View>(R.id.realView)
                        val touchView = rootView.findViewById<View>(R.id.touchView)
                        val topTouchView = rootView.findViewById<View>(R.id.topTouchView)
                        val parentView = targetView.parent as ViewGroup
                        parentView.layoutParams = (parentView.layoutParams as ConstraintLayout.LayoutParams).apply {
                            topMargin = (showLoading.y + showLoading.height).toInt()
                        }
                        play(ObjectAnimator
                                .ofFloat(targetView, "y", -UtilsExtension.dp2px(resources, 200f).toFloat(), 0f))
                                .with(ObjectAnimator
                                        .ofFloat(touchView, "alpha", 0f, 1f))
                                .with(ObjectAnimator
                                        .ofFloat(topTouchView, "alpha", 0f, 1f))
                    }
                }
                setOnExitAnimator {
                    AnimatorSet().apply {
                        duration = 1000L
                        val targetView = it.findViewById<View>(R.id.realView)
                        val touchView = it.findViewById<View>(R.id.touchView)
                        val topTouchView = it.findViewById<View>(R.id.topTouchView)
                        play(ObjectAnimator
                                .ofFloat(targetView, "y", 0f, -UtilsExtension.dp2px(resources, 200f).toFloat()))
                                .with(ObjectAnimator
                                        .ofFloat(touchView, "alpha", 1f, 0f))
                                .with(ObjectAnimator
                                        .ofFloat(topTouchView, "alpha", 1f, 0f))
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
                onKeyListenerForOptions { dialog, keyCode, event ->
                    return@onKeyListenerForOptions false
                }
                //阴影透明度
                dimAmount = 0f
                //动画
                animStyle = null
                //相对view的偏移
                //offsetX = -showLoading.width / 2
                //offsetY = -showLoading.height / 2
                //相对View的位置
                //gravityAsView = DialogGravity.CENTER_BOTTOM
                //showOnWindow的偏移
                //verticalMargin = dp2px(100f).toFloat()
                //horizontalMargin = dp2px(100f).toFloat()
                //isFullHorizontal 是否横向占满
                //isFullVertical 是否纵向占满 该纵向占满并非全屏，纵向占满会自动扣掉状态栏的高度
                //isFullVerticalOverStatusBar 该纵向占满全屏不会扣掉状态栏高度
                //touchCancel 是否点击屏幕区域取消（不包含返回按钮）
                //outCancel 是否点击外部取消 需要和touchCancel = false 一起使用
            }.onKeyListenerForDialog { genjiDialo, dialogInterFace, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (genjiDialo.getDialogOptions().canClick) {
                        genjiDialo.dismiss()
                        return@onKeyListenerForDialog true
                    } else {
                        return@onKeyListenerForDialog true
                    }
                }
                return@onKeyListenerForDialog false
            }.showOnWindow(supportFragmentManager)
        }
    }
}
