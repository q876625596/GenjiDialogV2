package com.ly.genjidialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.ly.genjidialog.extensions.AnimatorListenerEx
import com.ly.genjidialog.extensions.UtilsExtension.Companion.getScreenHeight
import com.ly.genjidialog.extensions.UtilsExtension.Companion.getScreenHeightOverStatusBar
import com.ly.genjidialog.extensions.UtilsExtension.Companion.getScreenWidth
import com.ly.genjidialog.listener.OnKeyListener
import com.ly.genjidialog.other.DialogGravity
import com.ly.genjidialog.other.DialogOptions
import com.ly.genjidialog.other.ViewHolder
import com.trello.rxlifecycle2.components.support.RxDialogFragment
import java.util.concurrent.atomic.AtomicBoolean

/*为了方便查看，我将每一个方法和其所需的对应属性放在一起*/
open class GenjiDialog : RxDialogFragment() {

    /*根布局*/
    lateinit var rootView: View

    /*绑定的activity*/
    lateinit var dialogActivity: AppCompatActivity

    /**
     * 执行顺序：2
     * 绑定activity，不建议使用fragment里面自带的getActivity()
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogActivity = context as AppCompatActivity
    }


    /**
     * 是否已经dismiss，避免主动调用dismiss的时候与onStop中触发两次相同事件
     */
    val dismissed = AtomicBoolean(false)

    /**
     * 保存UI状态的标签
     */
    private val options = "options"

    /**
     * dialog配置,所有配置都写在里面
     */
    private var dialogOptions: DialogOptions = DialogOptions()

    fun setDialogOptions(dialogOptions: DialogOptions): GenjiDialog {
        this.dialogOptions = dialogOptions
        return this
    }

    fun getDialogOptions(): DialogOptions {
        return dialogOptions
    }

    /*懒加载，根据dialogOptions.duration来延迟加载实现懒加载（曲线救国）*/
    open fun onLazy() {
        convertView(ViewHolder(rootView), this)
    }

    /**
     * 执行顺序：3
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //如果是继承了GenjiDialog，那么就会重写extendsOptions()方法
        //所以先检查是否重写该方法，如果重写了，就使用该方法返回的dialogOptions
        extendsOptions()?.let {
            dialogOptions = it
        }
        //设置dialog样式
        setStyle(dialogOptions.dialogStyle, dialogOptions.dialogThemeFun.invoke(this))
        //恢复保存的配置
        if (savedInstanceState != null) {
            dialogOptions = savedInstanceState.getParcelable(options)!!
        }
    }

    /**
     * 执行顺序：4
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //加载布局
        rootView = inflater.inflate(dialogOptions.layoutId, container, false)
        //unDisplayViewSize(view)
        if (!dialogOptions.isLazy) {
            convertView(ViewHolder(rootView), this)
        } else {
            //懒加载
            rootView.postDelayed({
                onLazy()
            }, dialogOptions.duration)
        }
        return rootView
    }

    /**
     * 数据绑定到视图/视图控件监听等
     */
    private fun convertView(holder: ViewHolder, dialog: GenjiDialog) {
        dialogOptions.convertListener?.convertView(holder, dialog)
    }

    /**
     *  执行顺序：5
     */
    override fun onStart() {
        super.onStart()
        //初始化配置
        initParams()
    }

    /**
     * 当继承GenjiDialog时，需要重写该方法，详见AAA
     */
    open fun extendsOptions(): DialogOptions? {
        return null
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     * （主要保存dialogOptions中的一些配置属性和监听，数据的保存还需自己手动来）
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(options, dialogOptions)
    }

    /**
     * 主动dismiss时会在onStop前调用（当采用特殊的view动画时，需要主动调用dismiss，才能生效退出动画）
     */
    override fun dismiss() {
        dialogOptions.exitAnimator.apply {
            //如果没自定义的view动画，那么直接执行
            if (this == null) {
                //如果没有执行过监听操作才执行，并且把监听设为已执行状态
                if (dismissed.compareAndSet(false, true)) {
                    executeDismissListener()
                    super.dismiss()

                }
            } else {//如果有动画
                //直接执行动画，该动画已经设置过监听，将会在结束动画时调用super.dismiss()方法
                this.start()
            }
        }

    }

    /**
     * 如果不是主动dismiss，而是点击屏幕或者返回键，
     * 就不会调用dismiss方法，直接走onStop
     * 所以需要在这里也调用dismiss的监听
     */
    override fun onStop() {
        super.onStop()
        //判断时候已经执行过dismiss的监听操作，如果已执行过，那么重新设为未执行监听状态
        if (dismissed.compareAndSet(true, false)) {
            return
        }
        executeDismissListener()
    }

    /**
     * 执行show时候的监听操作
     */
    private fun executeShowListener() {
        for (entry in dialogOptions.showDismissMap.entries) {
            if (entry.value.enableExecuteShowListener) {
                entry.value.onDialogShow()
            }
        }
    }

    /**
     * 执行dimiss时候的监听操作
     */
    private fun executeDismissListener() {
        for (entry in dialogOptions.showDismissMap.entries) {
            if (entry.value.enableExecuteDismissListener) {
                entry.value.onDialogDismiss()
            }
        }
    }

    /*进入动画的listener*/
    private lateinit var animatorEnterListener: AnimatorListenerEx
    /*退出动画的listener*/
    private lateinit var animatorExitListener: AnimatorListenerEx

    /*初始化进入动画的listener*/
    private fun initAnimatorEnterListener(): AnimatorListenerEx {
        animatorEnterListener = AnimatorListenerEx().onAnimatorStart {
            dialogOptions.canClick = false
        }.onAnimatorEnd {
            dialogOptions.canClick = true
        }
        return animatorEnterListener
    }

    /*初始化退出动画的listener*/
    private fun initAnimatorExitListener(): AnimatorListenerEx {
        animatorExitListener = AnimatorListenerEx().onAnimatorStart {
            dialogOptions.canClick = false
        }.onAnimatorEnd {
            //退出动画结束时调用super.dismiss()
            if (dismissed.compareAndSet(false, true)) {
                executeDismissListener()
                super.dismiss()
            }
            dialogOptions.canClick = true
        }
        return animatorExitListener
    }

    /**
     * 初始化配置
     */
    private fun initParams() {
        //设置dialog的初始化数据
        dialog.window?.let { window ->
            //设置dialog显示时，布局中view的自定义动画
            dialogOptions.setEnterAnimatorFun?.invoke(window.decorView.findViewById(android.R.id.content))?.let {
                it.addListener(initAnimatorEnterListener())
                dialogOptions.enterAnimator = it
            }
            //设置dialog隐藏时，布局中view的自定义动画
            dialogOptions.setExitAnimatorFun?.invoke(window.decorView.findViewById(android.R.id.content))?.let {
                it.addListener(initAnimatorExitListener())
                dialogOptions.exitAnimator = it
            }
            //设置dialog的statusBarColor
            window.statusBarColor = dialogOptions.dialogStatusBarColor
            //设置dialog的statusBar的显示模式
            dialogOptions.setStatusBarModeFun.invoke(this)
            //设置属性
            window.attributes = window.attributes?.apply {
                //调节灰色背景透明度[0-1]，默认0.3f
                dimAmount = dialogOptions.dimAmount
                //设置dialog宽度
                width = if (dialogOptions.width == 0) {
                    WindowManager.LayoutParams.WRAP_CONTENT
                } else {
                    dialogOptions.width
                }
                //设置dialog高度
                height = if (dialogOptions.height == 0) {
                    WindowManager.LayoutParams.WRAP_CONTENT
                } else {
                    dialogOptions.height
                }
                //当左右占满时，设置左右两边的平均边距
                if (dialogOptions.isFullHorizontal) {
                    horizontalMargin = 0f
                    width = getScreenWidth(resources) - 2 * dialogOptions.fullHorizontalMargin
                } else {
                    //没有占满的时候，设置水平方向的相对边距
                    horizontalMargin = when {
                        dialogOptions.horizontalMargin < 0 -> 0f
                        dialogOptions.horizontalMargin in 0..1 -> dialogOptions.horizontalMargin
                        else -> dialogOptions.horizontalMargin / getScreenWidth(resources)
                    }
                }
                //（不包含statusBar）当上下占满时，设置上下的平均边距
                if (dialogOptions.isFullVertical) {
                    verticalMargin = 0f
                    height = getScreenHeight(resources) - 2 * dialogOptions.fullVerticalMargin
                } else {
                    //没有占满的时候，设置水平方向的相对边距
                    verticalMargin = when {
                        dialogOptions.verticalMargin < 0 -> 0f
                        dialogOptions.verticalMargin in 0..1 -> dialogOptions.verticalMargin
                        else -> dialogOptions.verticalMargin / getScreenHeight(resources)
                    }
                }
                //（包含StatusBar）真正的全屏
                if (dialogOptions.isFullVerticalOverStatusBar) {
                    verticalMargin = 0f
                    height = getScreenHeightOverStatusBar(dialogActivity) - 2 * dialogOptions.fullVerticalMargin
                }
                //设置位置(如果设置了asView,那么gravity则永远为LEFT_TOP)
                gravity = dialogOptions.gravity.index
                //如果设置了asView，那么设置dialog的x，y值，将dialog显示在view附近
                if (dialogOptions.isAsView()) {
                    x = dialogOptions.dialogViewX
                    y = dialogOptions.dialogViewY
                }
            }
            //设置dialog进入时内部view的动画,如果animator动画不为空，那么执行animator动画
            dialogOptions.enterAnimator?.start()
            //否则执行animation动画
                    ?: apply {
                        dialogOptions.animStyle?.let {
                            window.setWindowAnimations(it)
                        }
                    }
        }
        //设置是否点击外部不消失
        isCancelable = dialogOptions.outCancel
        //设置是否点击屏幕区域不消失（点击返回键可消失）
        dialog.setCanceledOnTouchOutside(dialogOptions.touchCancel)
        //设置按键拦截事件，一般在全屏显示需要重写返回键时用到
        setOnKeyListener()
    }


    /**
     * 重写按钮监听
     */
    private fun setOnKeyListener() {
        /*if (fragmentManager == null) {
            return
        }
        if (dialogOptions.onKeyListener == null) {
            return
        }*/
        //boolean b = getFragmentManager().executePendingTransactions();

        //如果设置过特殊的动画，并且没有设置返回建的监听，那么默认设置一个返回键的监听
        if (dialogOptions.exitAnimator != null && dialogOptions.onKeyListener == null) {
            val onKey = object : OnKeyListener() {
                override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return if (getDialogOptions().canClick) {
                            dismiss()
                            true
                        } else {
                            true
                        }
                    }
                    return false
                }
            }
            dialog.setOnKeyListener(onKey)
        } else {//如果不是特殊动画，或者用户自定义了OnKeyListener，那么直接将onKeyListener设置
            dialogOptions.onKeyListener?.apply {
                dialog.setOnKeyListener(this)
            }
        }
    }

    /**
     * 该方法是为java准备的一个参数的showOnWindow方法
     * @param manager
     */
    fun showOnWindow(manager: FragmentManager): GenjiDialog {
        executeShowListener()
        dialogOptions.apply {
            this.gravity = dialogOptions.gravity
            this.animStyle = dialogOptions.animStyle
            removeAsView()
            loadAnim()
        }
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * 该方法是为java准备的两个参数的showOnWindow方法
     * @param manager
     * @param gravity dialog相对于屏幕的位置(默认为上一次设置的位置)
     */
    fun showOnWindow(manager: FragmentManager, gravity: DialogGravity = dialogOptions.gravity): GenjiDialog {
        executeShowListener()
        dialogOptions.apply {
            this.gravity = gravity
            this.animStyle = dialogOptions.animStyle
            removeAsView()
            loadAnim()
        }
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * show方法的执行顺序 ：1
     * 该方法java中为三参，kotlin为通用
     * @param manager
     * @param gravity dialog相对于屏幕的位置(默认为上一次设置的位置)
     * @param newAnim 新的动画(默认为上一次设置的动画)
     */
    fun showOnWindow(manager: FragmentManager, gravity: DialogGravity = dialogOptions.gravity, @StyleRes newAnim: Int? = dialogOptions.animStyle): GenjiDialog {
        executeShowListener()
        dialogOptions.apply {
            this.gravity = gravity
            this.animStyle = newAnim
            removeAsView()
            loadAnim()
        }
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * 该方法java中为四参，kotlin为通用
     * @param manager
     * @param gravity dialog相对于屏幕的位置(默认为上一次设置的位置)
     * @param newAnim 新的动画(默认为上一次设置的动画)
     * @param tag
     */
    fun showOnWindow(manager: FragmentManager, gravity: DialogGravity = dialogOptions.gravity, @StyleRes newAnim: Int? = dialogOptions.animStyle, tag: String?): GenjiDialog {
        executeShowListener()
        dialogOptions.apply {
            this.gravity = gravity
            this.animStyle = newAnim
            removeAsView()
            loadAnim()
        }
        super.show(manager, tag)
        return this
    }


    /**
     * @param manager
     * @param gravity dialog相对于屏幕的位置(默认为上一次设置的位置)
     * @param newAnim 新的动画(默认为上一次设置的动画)
     * @param tag
     */
    fun showNowOnWindow(manager: FragmentManager, gravity: DialogGravity = dialogOptions.gravity, @StyleRes newAnim: Int? = dialogOptions.animStyle, tag: String?): GenjiDialog {
        executeShowListener()
        dialogOptions.apply {
            this.gravity = gravity
            this.animStyle = newAnim
            removeAsView()
            loadAnim()
        }
        super.showNow(manager, tag)
        return this
    }

    /**
     * 将dialog显示在view附近
     * 该方法是为java准备的两个参数的showOnView方法
     * @param manager
     * @param view 被依赖的view
     */
    fun showOnView(manager: FragmentManager, view: View): GenjiDialog {
        executeShowListener()
        dialogOptions.dialogAsView(view, dialogOptions.gravityAsView, dialogOptions.animStyle, dialogOptions.offsetX, dialogOptions.offsetY)
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * 将dialog显示在view附近
     * 该方法是为java准备的三个参数的showOnView方法
     * @param manager
     * @param view 被依赖的view
     * @param gravityAsView 相对于该view的位置(默认为上一次设置的位置)
     */
    fun showOnView(manager: FragmentManager, view: View, gravityAsView: DialogGravity = dialogOptions.gravityAsView): GenjiDialog {
        executeShowListener()
        dialogOptions.dialogAsView(view, gravityAsView, dialogOptions.animStyle, dialogOptions.offsetX, dialogOptions.offsetY)
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * 将dialog显示在view附近
     * 该方法是为java准备的前四个参数的showOnView方法
     * @param manager
     * @param view 被依赖的view
     * @param gravityAsView 相对于该view的位置(默认为上一次设置的位置)
     * @param newAnim 新的动画(默认为上一次的动画效果)
     */
    fun showOnView(manager: FragmentManager, view: View, gravityAsView: DialogGravity = dialogOptions.gravityAsView, @StyleRes newAnim: Int? = dialogOptions.animStyle): GenjiDialog {
        executeShowListener()
        dialogOptions.dialogAsView(view, gravityAsView, newAnim, dialogOptions.offsetX, dialogOptions.offsetY)
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * 将dialog显示在view附近
     * @param manager
     * @param view 被依赖的view
     * @param gravityAsView 相对于该view的位置(默认为上一次设置的位置)
     * @param newAnim 新的动画(默认为上一次的动画效果)
     * @param offsetX x轴的偏移量，(默认为上一次设置过的偏移量)
     * 偏移量的定义请看{@link DialogOptions#dialogAsView(View,DialogGravity,Int,Int,Int) DialogOptions.dialogAsView}
     * @param offsetY y轴的偏移量，(默认为上一次设置过的偏移量)
     */
    fun showOnView(manager: FragmentManager, view: View, gravityAsView: DialogGravity = dialogOptions.gravityAsView, @StyleRes newAnim: Int? = dialogOptions.animStyle, offsetX: Int = dialogOptions.offsetX, offsetY: Int = dialogOptions.offsetY): GenjiDialog {
        executeShowListener()
        dialogOptions.dialogAsView(view, gravityAsView, newAnim, offsetX, offsetY)
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

}