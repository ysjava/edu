package com.sandgrains.edu.learnmodel

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
class CircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attrs, defStyleAttr) {
    private var color = Color.RED
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.map)
    val camera = Camera()

    //默认宽高
    private val defWidth = DisplayUtil.dip2px(context, 200f)
    private val defHeight = DisplayUtil.dip2px(context, 200f)

    private var heightSpecSize = defHeight
    private var widthSpecSize = defWidth

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        color = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED)
        typedArray.recycle()

        paint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //对wrap_content处理
        val widthSpecModel = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecModel = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpecModel == MeasureSpec.AT_MOST && heightSpecModel == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defWidth, defHeight)
        } else if (widthSpecModel == MeasureSpec.AT_MOST) {
            this.heightSpecSize = heightSpecSize
            setMeasuredDimension(defWidth, heightSpecSize)
        } else if (heightSpecModel == MeasureSpec.AT_MOST) {
            this.widthSpecSize = widthSpecSize
            setMeasuredDimension(widthSpecSize, defHeight)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
//        canvas.scale(0.5f,0.5f,defWidth/2.toFloat(),defHeight/2.toFloat())
//        camera.save()
//        camera.rotateX(30f)
//        canvas.translate(left.toFloat(),top.toFloat())
//        camera.applyToCanvas(canvas)
//        canvas.translate(-left.toFloat(),-top.toFloat())
//        camera.restore()
        Log.e("ZS","bitmap.height${bitmap.height}  defHeight:$defHeight ")
        bitmap.height
        val left = (width - bitmap.width) / 2f
        val top = (height - bitmap.height) / 2f
        canvas.drawBitmap(bitmap, left, top, paint)

        canvas.restore()
    }

}