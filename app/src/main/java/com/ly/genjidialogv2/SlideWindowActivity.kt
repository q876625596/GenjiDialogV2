package com.ly.genjidialogv2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.ly.genjidialog.extensions.UtilsExtension
import com.ly.genjidialog.extensions.convertListenerFun
import com.ly.genjidialog.extensions.newGenjiDialog
import kotlinx.android.synthetic.main.activity_slide.*


class SlideWindowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)

        /**********带遮罩的滑出动画的dialog *************/
        slideForBottom.setOnClickListener { view ->
            newGenjiDialog { genjiDialog ->
                //设置布局
                layoutId = R.layout.slide_view_bottom
                //isLazy = true
                //设置横纵向占满
                isFullHorizontal = true
                isFullVerticalOverStatusBar = true
                //阴影透明度
                dimAmount = 0f
                //处理事件/数据绑定
                convertListenerFun { holder, dialog ->
                    //设置点击realView以外的部分就dismiss
                    holder.setOnClickListener(R.id.bottomTouchView) {
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
                        duration = 500L
                        val realView = rootView.findViewById<View>(R.id.realView)
                        val touchView = rootView.findViewById<View>(R.id.bottomTouchView)
                        val topTouchView = rootView.findViewById<View>(R.id.topTouchView)
                        val maskLayout = rootView.findViewById<View>(R.id.maskLayout)
                        //给realView的父布局(遮罩布局)设置距顶部margin
                        maskLayout?.apply {
                            layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                                topMargin = (slideForBottom.y + slideForBottom.height).toInt()
                            }
                        }
                        play(ObjectAnimator
                                .ofFloat(realView, "y", -UtilsExtension.dp2px(resources, 200f).toFloat(), 0f))
                                .with(ObjectAnimator
                                        .ofFloat(touchView, "alpha", 0f, 1f))
                                .with(ObjectAnimator
                                        .ofFloat(topTouchView, "alpha", 0f, 1f))
                    }
                }
                setOnExitAnimator {
                    //退出动画
                    AnimatorSet().apply {
                        duration = 500L
                        val realView = it.findViewById<View>(R.id.realView)
                        val touchView = it.findViewById<View>(R.id.bottomTouchView)
                        val topTouchView = it.findViewById<View>(R.id.topTouchView)
                        play(ObjectAnimator
                                .ofFloat(realView, "y", 0f, -UtilsExtension.dp2px(resources, 200f).toFloat()))
                                .with(ObjectAnimator
                                        .ofFloat(touchView, "alpha", 1f, 0f))
                                .with(ObjectAnimator
                                        .ofFloat(topTouchView, "alpha", 1f, 0f))
                    }
                }
            }.showOnWindow(supportFragmentManager)
        }
        slideForTop.setOnClickListener { view ->
            newGenjiDialog { genjiDialog ->
                //设置布局
                layoutId = R.layout.slide_view_top
                //isLazy = true
                //设置横纵向占满
                isFullHorizontal = true
                isFullVerticalOverStatusBar = true
                //阴影透明度
                dimAmount = 0f
                //处理事件/数据绑定
                convertListenerFun { holder, dialog ->
                    //设置点击realView以外的部分就dismiss
                    holder.setOnClickListener(R.id.bottomTouchView) {
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
                        duration = 500L
                        val realView = rootView.findViewById<View>(R.id.realView)
                        val touchView = rootView.findViewById<View>(R.id.bottomTouchView)
                        val topTouchView = rootView.findViewById<View>(R.id.topTouchView)
                        val maskLayout = rootView.findViewById<View>(R.id.maskLayout)
                        val maskLayoutHeight = UtilsExtension.unDisplayViewSize(maskLayout)[1]
                        //给realView的父布局(遮罩布局)设置距顶部margin
                        maskLayout?.apply {
                            layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                                bottomMargin = UtilsExtension.getScreenHeightOverStatusBar(this@SlideWindowActivity) - slideForTop.y.toInt()
                                //可以把maskLayout的宽设置为对应View的宽度
                                width = slideForTop.width
                            }
                        }
                        play(ObjectAnimator
                                .ofFloat(realView, "y", maskLayoutHeight.toFloat(), 0f))
                                .with(ObjectAnimator
                                        .ofFloat(touchView, "alpha", 0f, 1f))
                                .with(ObjectAnimator
                                        .ofFloat(topTouchView, "alpha", 0f, 1f))
                    }
                }
                setOnExitAnimator {
                    //退出动画
                    AnimatorSet().apply {
                        duration = 500L
                        val realView = it.findViewById<View>(R.id.realView)
                        val touchView = it.findViewById<View>(R.id.bottomTouchView)
                        val topTouchView = it.findViewById<View>(R.id.topTouchView)
                        val maskLayout = it.findViewById<View>(R.id.maskLayout)
                        val maskLayoutHeight = UtilsExtension.unDisplayViewSize(maskLayout)[1]
                        play(ObjectAnimator
                                .ofFloat(realView, "y", 0f, maskLayoutHeight.toFloat()))
                                .with(ObjectAnimator
                                        .ofFloat(touchView, "alpha", 1f, 0f))
                                .with(ObjectAnimator
                                        .ofFloat(topTouchView, "alpha", 1f, 0f))
                    }
                }
            }.showOnWindow(supportFragmentManager)
        }
        slideForRight.setOnClickListener { view ->
            newGenjiDialog { genjiDialog ->
                //设置布局
                layoutId = R.layout.slide_view_right
                //isLazy = true
                //设置横纵向占满
                isFullHorizontal = true
                isFullVerticalOverStatusBar = true
                //阴影透明度
                dimAmount = 0f
                //处理事件/数据绑定
                convertListenerFun { holder, dialog ->
                    //设置点击realView以外的部分就dismiss
                    holder.setOnClickListener(R.id.fullTouchView) {
                        if (canClick) {
                            dialog.dismiss()
                        }
                    }
                }
                setOnEnterAnimator { rootView ->
                    //在此处设置进入动画
                    AnimatorSet().apply {
                        duration = 500L
                        val realView = rootView.findViewById<View>(R.id.realView)
                        val fullTouchView = rootView.findViewById<View>(R.id.fullTouchView)
                        val maskLayout = rootView.findViewById<View>(R.id.maskLayout)
                        val maskLayoutWidth = UtilsExtension.unDisplayViewSize(maskLayout)[0]
                        //给realView的父布局(遮罩布局)设置距顶部margin
                        maskLayout?.apply {
                            layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                                topMargin = slideForRight.y.toInt()
                                marginStart = slideForRight.x.toInt() + slideForRight.width
                                height = slideForRight.height
                            }
                        }
                        play(ObjectAnimator
                                .ofFloat(realView, "x", -maskLayoutWidth.toFloat(), 0f))
                                .with(ObjectAnimator
                                        .ofFloat(fullTouchView, "alpha", 0f, 1f))
                    }
                }
                setOnExitAnimator {
                    //退出动画
                    AnimatorSet().apply {
                        duration = 500L
                        val realView = it.findViewById<View>(R.id.realView)
                        val fullTouchView = it.findViewById<View>(R.id.fullTouchView)
                        val maskLayout = it.findViewById<View>(R.id.maskLayout)
                        val maskLayoutWidth = UtilsExtension.unDisplayViewSize(maskLayout)[0]
                        play(ObjectAnimator
                                .ofFloat(realView, "x", 0f, -maskLayoutWidth.toFloat()))
                                .with(ObjectAnimator
                                        .ofFloat(fullTouchView, "alpha", 1f, 0f))
                    }
                }
            }.showOnWindow(supportFragmentManager)
        }

        slideForLeft.setOnClickListener { view ->
            newGenjiDialog { genjiDialog ->
                //设置布局
                layoutId = R.layout.slide_view_left
                //isLazy = true
                //设置横纵向占满
                isFullHorizontal = true
                isFullVerticalOverStatusBar = true
                //阴影透明度
                dimAmount = 0f
                //处理事件/数据绑定
                convertListenerFun { holder, dialog ->
                    //设置点击realView以外的部分就dismiss
                    holder.setOnClickListener(R.id.fullTouchView) {
                        if (canClick) {
                            dialog.dismiss()
                        }
                    }
                }
                setOnEnterAnimator { rootView ->
                    //在此处设置进入动画
                    AnimatorSet().apply {
                        duration = 500L
                        val realView = rootView.findViewById<View>(R.id.realView)
                        val fullTouchView = rootView.findViewById<View>(R.id.fullTouchView)
                        val maskLayout = rootView.findViewById<View>(R.id.maskLayout)
                        val maskLayoutWidth = UtilsExtension.unDisplayViewSize(maskLayout)[0]
                        //给realView的父布局(遮罩布局)设置距顶部margin
                        maskLayout?.apply {
                            layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                                topMargin = slideForLeft.y.toInt()
                                marginEnd = UtilsExtension.getScreenWidth(resources) - slideForLeft.x.toInt()
                                height = slideForLeft.height
                            }
                        }
                        play(ObjectAnimator
                                .ofFloat(realView, "x", maskLayoutWidth.toFloat(), 0f))
                                .with(ObjectAnimator
                                        .ofFloat(fullTouchView, "alpha", 0f, 1f))
                    }
                }
                setOnExitAnimator {
                    //退出动画
                    AnimatorSet().apply {
                        duration = 500L
                        val realView = it.findViewById<View>(R.id.realView)
                        val fullTouchView = it.findViewById<View>(R.id.fullTouchView)
                        val maskLayout = it.findViewById<View>(R.id.maskLayout)
                        val maskLayoutWidth = UtilsExtension.unDisplayViewSize(maskLayout)[0]
                        play(ObjectAnimator
                                .ofFloat(realView, "x", 0f, maskLayoutWidth.toFloat()))
                                .with(ObjectAnimator
                                        .ofFloat(fullTouchView, "alpha", 1f, 0f))
                    }
                }
            }.showOnWindow(supportFragmentManager)
        }
    }
}
