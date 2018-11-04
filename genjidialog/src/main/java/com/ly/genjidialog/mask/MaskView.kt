package com.ly.customviewlearn

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class MaskView : View {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var trueWidth = 0
    private var trueHeight = 0

    private var highlightAreaType = HighlightAreaType.TRANSPARENT_CUBE

    var highlightArea = HighlightArea()
        set(value) {
            field = value
            invalidate()
        }

    //需要绘制的bitmap
    var highlightBitmap: Bitmap? = null
        set(value) {
            field = value
            highlightAreaType = HighlightAreaType.BITMAP
            invalidate()
        }
    //遮罩的透明度，0-1
    var maskAlpha = 0.5f
        set(value) {
            field = value
            invalidate()
        }
    //背景色，固定透明，防止与遮罩层颜色冲突
    private var bgColor = Color.TRANSPARENT
    //整个view的rect
    private val viewFullRect = Rect()
    //混合模式
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // 加载自定义属性集合CircleView
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaskView)
        highlightAreaType = when (a.getInt(R.styleable.MaskView_mask_highlightAreaType, HighlightAreaType.TRANSPARENT_CUBE.value)) {
            0 -> HighlightAreaType.TRANSPARENT_CUBE
            1 -> HighlightAreaType.BITMAP
            else -> HighlightAreaType.TRANSPARENT_CUBE
        }
        highlightArea.areaRect.set(
                a.getDimension(R.styleable.MaskView_mask_highlightAreaLeft, 0f),
                a.getDimension(R.styleable.MaskView_mask_highlightAreaTop, 0f),
                a.getDimension(R.styleable.MaskView_mask_highlightAreaRight, 0f),
                a.getDimension(R.styleable.MaskView_mask_highlightAreaBottom, 0f)
        )
        val radius = a.getDimension(R.styleable.MaskView_mask_highlightAreaRadius, 0f)
        highlightArea.radiusX = radius
        highlightArea.radiusY = radius
        a.getResourceId(R.styleable.MaskView_mask_highlightBitmap, 0).apply {
            if (this != 0) {
                highlightBitmap = BitmapFactory.decodeResource(resources, this)
            }
        }
        maskAlpha = a.getFloat(R.styleable.MaskView_mask_Alpha, 0.5f)

    }

    override fun onDraw(canvas: Canvas) {
        //设置背景色-默认透明，防止底色是黑色
        setBackgroundColor(bgColor)
        //开始离屏渲染
        val saved = canvas.saveLayer(
                viewFullRect.left.toFloat(),
                viewFullRect.top.toFloat(),
                viewFullRect.right.toFloat(),
                viewFullRect.bottom.toFloat(), paint)
        //设置画笔颜色-遮罩颜色
        paint.color = Color.argb((255 * maskAlpha).toInt(), 0, 0, 0)
        //在屏幕外绘制遮罩
        canvas.drawRect(viewFullRect, paint)
        //设置镂空的颜色-实际上可随意设置颜色
        paint.color = Color.WHITE
        //设置图像混合模式，这里设置的是将新图像与旧图像所重合的位置镂空
        paint.xfermode = xfermode
        //在屏幕外绘制需要镂空的位置
        canvas.drawRoundRect(highlightArea.areaRect, highlightArea.radiusX, highlightArea.radiusY, paint)
        //关闭混合
        paint.xfermode = null
        //结束离屏渲染
        canvas.restoreToCount(saved)
        //如果需要在镂空位置绘制bitmap
        if (highlightAreaType == HighlightAreaType.BITMAP) {
            highlightBitmap?.let {
                canvas.drawBitmap(
                        it,
                        viewFullRect, highlightArea.areaRect, paint)
            }
        }

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        trueWidth = right - left
        trueHeight = bottom - top
        //设置遮罩层-大小为该 MaskView 在页面上的实际大小
        viewFullRect.set(0, 0, trueWidth, trueHeight)
    }

}