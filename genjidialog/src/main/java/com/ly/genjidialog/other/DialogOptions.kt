package com.ly.genjidialog.other

import android.animation.Animator
import android.graphics.Color
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.R
import com.ly.genjidialog.extensions.UtilsExtension.Companion.unDisplayViewSize
import com.ly.genjidialog.listener.DialogShowOrDismissListener
import com.ly.genjidialog.listener.OnKeyListener
import com.ly.genjidialog.listener.ViewConvertListener

open class DialogOptions() : Parcelable {

    /*****************布局样式*******************/

    /**
     * 布局
     */
    @LayoutRes
    open var layoutId = R.layout.loading_layout


    /**
     *  dialog样式
     */
    var dialogStyle = DialogFragment.STYLE_NO_TITLE

    /**
     * dialog主题（如果有其他需求可重写该方法）
     */
    var dialogThemeFun: (genjiDialog: GenjiDialog) -> Int = {
        it.getMyActivity().run {
            //如果activity是否是占满全屏并且依旧保留状态栏（沉浸式状态栏）
            if (this.window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    || this.window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) {
                R.style.GenjiDialogFullScreen
            } else {//非沉浸式状态栏（包括普通的带状态栏页面以及全屏无状态栏页面）
                R.style.GenjiDialog
            }
        }
    }

    /*****************动画相关*******************/

    /**
     * dialog进出的animation动画
     * 此动画为普通动画，
     */
    @StyleRes
    var animStyle: Int? = 0

    /*是否可以触发取消，比如在动画开始时将此属性设置false，防止在动画进行时，被再次触发动画*/
    var canClick = true

    /**
     * dialog进出的自定义animator动画
     */
    var enterAnimator: Animator? = null
    var exitAnimator: Animator? = null

    /*懒加载的延时(根据自己动画的时长来设置)*/
    var duration: Long = 0L

    var setEnterAnimatorFun: ((contentView: View) -> Animator)? = null
    var setExitAnimatorFun: ((contentView: View) -> Animator)? = null

    fun setOnEnterAnimator(listener: (contentView: View) -> Animator) {
        setEnterAnimatorFun = listener
    }

    fun setOnExitAnimator(listener: (contentView: View) -> Animator) {
        setExitAnimatorFun = listener
    }

    /*是否是懒加载*/
    var isLazy = false

    /**
     * dialog的statusBarColor
     * 如果Activity使用沉浸式状态栏
     * 此时就需要设置dialog的statusBarColor
     * 默认透明
     * */
    var dialogStatusBarColor = Color.TRANSPARENT

    /**
     * 宽度 单位：px(如果不设置，那么默认为布局中所有控件所占的最大空间，
     * 如果需要达到相应的效果，建议在布局中添加一层View作为背景，例如aaa.xml)
     * 但是经过测试有时候依旧会出一些显示问题，所以尽量还是设置好宽高
     */
    var width = 0

    /**
     * 高度 单位：px
     */
    var height = 0

    /**
     * 是否横向占满
     */
    var isFullHorizontal = false

    /**
     * 是否纵向占满
     * 该纵向占满并非全屏，纵向占满会自动扣掉状态栏的高度
     */
    var isFullVertical = false

    /**
     * 该纵向占满全屏不会扣掉状态栏高度,是真正的全屏
     */
    var isFullVerticalOverStatusBar = false

    /**
     * 上下边距
     * 以下两个margin分别适用于横纵向不占满的情况
     * 并且根据gravity来判定margin的方向，
     * 例如：当gravity为left和top的时候，
     * 此时的verticalMargin则代表距离left的Margin
     * 此时的horizontalMargin就代表距离top的Margin
     * (CENTER_CENTER的时候默认是相对于left和top)
     * 如果取值范围为[0-1]，代表相对于屏幕的百分比（屏幕宽高*margin取值）
     * 如果取值范围大于1，那么会自动计算出相对于屏幕宽高的比例（margin取值/屏幕宽高）
     * 该属性同样会影响dialog在依附于view上时的偏移，因此在依附于view的时候建议设置为0f
     */
    var verticalMargin = 0f

    /**
     * 左右边距
     */
    var horizontalMargin = 0f

    /**
     * 以下两个margin分别用于横纵向占满的情况
     * 垂直方向上顶部和底部的margin 单位： px
     */
    var fullVerticalMargin = 0

    /**
     * 水平方向上左右两边的margin  单位： px
     */
    var fullHorizontalMargin = 0

    /**
     * 灰度深浅
     */
    var dimAmount = 0.3f

    /**
     * dialog的位置（默认居中）
     */
    var gravity = DialogGravity.CENTER_CENTER

    /**
     * 当dialog依附于view时的位置（默认在下方居中）
     */
    var gravityAsView = DialogGravity.CENTER_BOTTOM

    /**
     * x轴坐标值
     */
    var dialogViewX: Int = 0

    /**
     * y轴坐标值
     */
    var dialogViewY: Int = 0

    /**
     * 当dialog依附在view上时x轴的偏移量
     */
    var offsetX: Int = 0

    /**
     * 当dialog依附在view上时y轴的偏移量
     */
    var offsetY: Int = 0

    /**
     * 是否依附在view上
     */
    private var asView: Boolean = false

    /**
     * 是否点击屏幕区域取消（不包含返回按钮）
     */
    var touchCancel = true

    /**
     * 是否点击外部取消
     * 当 touchCancel == true时此属性无效，
     * 必须是 touchCancel和该属性均为false时，那么点击屏幕区域和返回按钮都不能关闭dialog
     */
    var outCancel = true

    /**
     * 显示与消失的监听
     */
    var showDismissMap = mutableMapOf<String, DialogShowOrDismissListener>()

    /**
     * 按钮监听
     */
    var onKeyListener: OnKeyListener? = null

    /**
     * 事件监听
     */
    var convertListener: ViewConvertListener? = null


    /**
     * 返回是否是依附在view上
     */
    fun isAsView(): Boolean {
        return asView
    }

    /**
     * 移除对View的依附
     */
    fun removeAsView() {
        asView = false
    }

    /**
     * dialog顶部导航栏设置
     * 主要用于设置在statusBar的影响下，dialog显示时y轴错位的情况
     * 当activity是沉浸式状态栏时（无论是否预留statusBar的高度）：dialog的是可占满全屏的
     * 当activity非沉浸式状态栏时：dialog是不可占满全屏的
     * 该方法对象可重写
     */
    var setStatusBarModeFun: (genjiDialog: GenjiDialog) -> Unit = { genjiDialog ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            genjiDialog.getMyActivity().apply {
                //如果activity是否是占满全屏并且依旧保留状态栏（沉浸式状态栏）
                if (this.window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        || this.window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) {
                    //是否预留statusBar
                    (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0).fitsSystemWindows.let {
                        //（与activity保持一致）
                        genjiDialog.dialog.window.decorView.fitsSystemWindows = it
                        if (it) {//无论是否预留了statusBar，都将dialog设置可占满全屏
                            genjiDialog.dialog.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            //此时可以设置dialogStatusBarColor
                            //genjiDialog.dialog.window.statusBarColor = dialogStatusBarColor
                        } else {//如果不预留statusrBar，那么dialog设置可占满全屏
                            genjiDialog.dialog.window.decorView.systemUiVisibility = window.decorView.systemUiVisibility
                        }
                    }
                    //genjiDialog.dialog.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                } else {//非沉浸式状态栏（包括普通的带状态栏页面以及全屏无状态栏页面）此时的statusBarColor不生效
                    genjiDialog.dialog.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    //genjiDialog.dialog.window.statusBarColor = dialogStatusBarColor
                }
            }
        } else {//4.4 全透明状态栏
            genjiDialog.dialog.window.addFlags(genjiDialog.getMyActivity().window.attributes.flags)
        }
    }

    /**
     * 可调用此方法重写dialogThemeFun
     */
    fun setDialogTheme(function: (genjiDialog: GenjiDialog) -> Int) {
        dialogThemeFun = function
    }

    /**
     * 可调用此方法来重写setStatusBarModeFun
     */
    fun setStatusMode(function: (genjiDialog: GenjiDialog) -> Unit) {
        setStatusBarModeFun = function
    }

    /**
     * 依附在view上
     *
     *  1.如果dialog在view的左上角(DialogGravity.LEFT_TOP)
     *      该offsetX值表示dialog右边线相对于view左边线的偏移
     *      offsetY值表示dialog下边线相对于view上边线的偏移
     *      -------------
     *      |           |
     *      |   dialog  |
     *      |           |
     *      -------------
     *                   ------------
     *                   |   view   |
     *                   ------------
     *
     *  2.如果dialog在view的上部居中(DialogGravity.CENTER_TOP)
     *      此时offsetX值表示dialog与view纵向中心线的偏移量
     *       offsetY值表示dialog下边线相对于view上边线的偏移
     *                   ------------
     *                   |   view   |
     *                   ------------
     *                   -------------
     *                   |           |
     *                   |   dialog  |
     *                   |           |
     *                   -------------
     *                         ^
     *                        纵
     *                        向
     *                        中
     *                        心
     *                        线
     *
     *  3.如果dialog在view的右上方(DialogGravity.RIGHT_TOP)
     *      该offsetX值表示dialog左边线相对于view右边线的偏移
     *      offsetY值表示dialog下边线相对于view上边线的偏移
     *
     *                                -------------
     *                                |           |
     *                                |   dialog  |
     *                                |           |
     *                                -------------
     *                   -------------
     *                   |    view   |
     *                   -------------
     *
     * 4.如果dialog在view的左边居中(DialogGravity.LEFT_CENTER)
     *      该offsetX值表示dialog右边线相对于view左边线的偏移
     *      offsetY值表示dialog与view横向中心线的偏移
     *
     *      -------------
     *      |           |-------------
     *      |   dialog  ||    view   | <中心线
     *      |           |-------------
     *      -------------
     *
     * 5.如果dialog在view的右边居中(DialogGravity.RIGHT_CENTER)
     *      该offsetX值表示dialog左边线相对于view右边线的偏移
     *      offsetY值表示dialog与view横向中心线的偏移
     *
     *                                -------------
     *                   -------------|           |
     *                   |    view   ||   dialog  |  <中心线
     *                   -------------|           |
     *                                -------------
     *
     * 6.如果dialog在view的左下角(DialogGravity.RIGHT_CENTER)
     *      该offsetX值表示dialog右边线相对于view左边线的偏移
     *      offsetY值表示dialog上边线相对于view下边线的偏移
     *
     *                   -------------
     *                   |    view   |
     *                   -------------
     *      -------------
     *      |           |
     *      |   dialog  |
     *      |           |
     *      -------------
     *
     * 7.如果dialog在view的下方居中(DialogGravity.CENTER_BOTTOM)
     *      该offsetX值表示dialog与view纵向中心线的偏移
     *      offsetY值表示dialog上边线相对于view下边线的偏移
     *
     *                   -------------
     *                   |    view   |
     *                   -------------
     *                   -------------
     *                   |           |
     *                   |   dialog  |
     *                   |           |
     *                   -------------
     *
     * 8.如果dialog在view的右下方(DialogGravity.CENTER_BOTTOM)
     *      该offsetX值表示dialog左边线相对于view右边线的偏移
     *      offsetY值表示dialog上边线相对于view下边线的偏移
     *
     *                   -------------
     *                   |    view   |
     *                   -------------
     *                                -------------
     *                                |           |
     *                                |   dialog  |
     *                                |           |
     *                                -------------
     *
     * 9.如果dialog与view的中心重合(DialogGravity.CENTER_BOTTOM)
     *      该offsetX值表示dialog与view纵向中心线的偏移
     *      offsetY值表示dialog与view横向中心线的偏移
     *                 -------------------
     *                 |                 |
     *                 |                 |
     *                 |  -------------  |
     *                 |  |    view   |  |    <横向中心线
     *                 |  -------------  |
     *                 |                 |
     *                 |      dialog     |
     *                 -------------------
     *                          ^
     *                         纵
     *                         向
     *                         中
     *                         心
     *                         线
     *
     * @param view 目标view
     * @param gravityAsView 依附于view的位置
     * @param newAnim 新的动画
     * @param offsetX x轴偏移量
     * @param offsetY y轴偏移量
     */
    fun dialogAsView(view: View, gravityAsView: DialogGravity = this.gravityAsView, @StyleRes newAnim: Int? = this.animStyle, offsetX: Int = this.offsetX, offsetY: Int = this.offsetY) {
        //依附于view
        asView = true
        //设置dialog的位置在屏幕的左上角，因为这样才能更好的计算最终位置
        this.gravity = DialogGravity.LEFT_TOP
        //设置新的动画
        if (animStyle != newAnim) {
            animStyle = newAnim
        }
        //设置偏移量
        this.offsetX = offsetX
        this.offsetY = offsetY
        //获取到dialogView的宽高
        val dialogViewSize = unDisplayViewSize(LayoutInflater.from(view.context).inflate(layoutId, null))
        val dialogViewWidth = dialogViewSize[0]
        val dialogViewHeight = dialogViewSize[1]
        //设置view的数据
        val viewWidth = view.width
        val viewHeight = view.height
        val viewX = view.x.toInt()
        val viewY = view.y.toInt()
        //设置依附于view的位置
        this.gravityAsView = gravityAsView
        //根据gravity判断显示的位置
        when (gravityAsView) {
        //dialog显示在view的左上角
            DialogGravity.LEFT_TOP -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y100
                this.dialogViewX = viewX - if (width != 0) width else dialogViewWidth + offsetX
                this.dialogViewY = viewY - if (height != 0) height else dialogViewHeight + offsetY
            }
        //dialog显示在view的上方
            DialogGravity.CENTER_TOP -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX50Y100
                this.dialogViewX = viewX - ((if (width != 0) width else dialogViewWidth) - viewWidth) / 2 + offsetX
                this.dialogViewY = viewY - if (height != 0) height else dialogViewHeight + offsetY
            }
        //dialog显示在view的右上角
            DialogGravity.RIGHT_TOP -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y100
                this.dialogViewX = viewX + viewWidth + offsetX
                this.dialogViewY = viewY - if (height != 0) height else dialogViewHeight + offsetY
            }
        //dialog显示在view的左边
            DialogGravity.LEFT_CENTER -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y50
                this.dialogViewX = viewX - if (width != 0) width else dialogViewWidth + offsetX
                this.dialogViewY = viewY - ((if (height != 0) height else dialogViewHeight) - viewHeight) / 2 + offsetY
            }
        //dialog显示在view的正中心
            DialogGravity.CENTER_CENTER -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX50Y50
                this.dialogViewX = viewX - ((if (width != 0) width else dialogViewWidth) - viewWidth) / 2 + offsetX
                this.dialogViewY = viewY - ((if (height != 0) height else dialogViewHeight) - viewHeight) / 2 + offsetY
            }
        //dialog显示在view的右边
            DialogGravity.RIGHT_CENTER -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y50
                this.dialogViewX = viewX + viewWidth + offsetX
                this.dialogViewY = viewY - ((if (height != 0) height else dialogViewHeight) - viewHeight) / 2 + offsetY
            }
        //dialog显示在view的左下角
            DialogGravity.LEFT_BOTTOM -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y0
                this.dialogViewX = viewX - if (width != 0) width else dialogViewWidth + offsetX
                this.dialogViewY = viewY + viewHeight + offsetY
            }
        //dialog显示在view的下方
            DialogGravity.CENTER_BOTTOM -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX50Y0
                this.dialogViewX = viewX - ((if (width != 0) width else dialogViewWidth) - viewWidth) / 2 + offsetX
                this.dialogViewY = viewY + viewHeight + offsetY
            }
        //dialog显示在view的右下角
            DialogGravity.RIGHT_BOTTOM -> {
                if (this.animStyle == 0) this.animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y0
                this.dialogViewX = viewX + viewWidth + offsetX
                this.dialogViewY = viewY + viewHeight + offsetY
            }
            else -> {
                if (this.animStyle == 0) this.animStyle = R.style.AlphaEnterExitAnimation
                this.dialogViewX = viewX - ((if (width != 0) width else dialogViewWidth) - viewWidth) / 2 + offsetX
                this.dialogViewY = viewY - ((if (height != 0) height else dialogViewHeight) - viewHeight) / 2 + offsetY
            }
        }

    }

    /**
     * 当没有设置动画的时候，该方法会设置一个默认动画
     */
    fun loadAnim() {
        if (animStyle == null) {
            return
        }
        if (animStyle != 0) {
            return
        }
        //根据dialog的位置来设置默认anim
        when (gravity.index) {

        //左上(默认动画从左至右加速减速)
            DialogGravity.LEFT_TOP.index,
                //左中(默认动画从左至右加速减速)
            DialogGravity.LEFT_CENTER.index,
                //左下(默认动画从左至右加速减速)
            DialogGravity.LEFT_BOTTOM.index ->
                animStyle = R.style.LeftTransAlphaADAnimation

        //右上(默认动画从右至左加速减速)
            DialogGravity.RIGHT_TOP.index,
                //右中(默认动画从右至左加速减速)
            DialogGravity.RIGHT_CENTER.index,
                //右下(默认动画从右至左加速减速)
            DialogGravity.RIGHT_BOTTOM.index ->
                animStyle = R.style.RightTransAlphaADAnimation

        //正中(默认动画渐入渐出)
            DialogGravity.CENTER_CENTER.index ->
                animStyle = R.style.AlphaEnterExitAnimation

        //中上(默认动画从上至下加速减速)
            DialogGravity.CENTER_TOP.index ->
                animStyle = R.style.TopTransAlphaADAnimation

        //中下(默认动画从下至上加速减速)
            DialogGravity.CENTER_BOTTOM.index ->
                animStyle = R.style.BottomTransAlphaADAnimation
        //默认动画淡入淡出
            else ->
                animStyle = R.style.AlphaEnterExitAnimation
        }
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DialogOptions> = object : Parcelable.Creator<DialogOptions> {
            override fun createFromParcel(source: Parcel): DialogOptions = DialogOptions(source)
            override fun newArray(size: Int): Array<DialogOptions?> = arrayOfNulls(size)
        }
    }
}
