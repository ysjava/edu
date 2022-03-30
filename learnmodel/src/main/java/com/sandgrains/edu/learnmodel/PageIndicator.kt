package com.sandgrains.edu.learnmodel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.number.Scale
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PageIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {
    var scale: Float = 0f
        set(value) {
            if (value > 1 || value < 0) return
            field = value
            postInvalidate()
        }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(500, 100)
    }

    init {

        paint.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val top = (height - 20) / 2f
        val left = (width - 180) / 2f
        canvas.drawRoundRect(left, top, left + 90f - 30f * scale, top + 20, 15f, 15f, paint)
        canvas.drawRoundRect(left + 120f - 30f * scale, top, left + 180f, top + 20, 15f, 15f, paint)

    }

}