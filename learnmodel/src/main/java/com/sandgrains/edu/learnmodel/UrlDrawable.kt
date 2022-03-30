package com.sandgrains.edu.learnmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable

open class UrlDrawable(protected val cBitmap: Bitmap? = null): BitmapDrawable() {
    override fun draw(canvas: Canvas) {

        // override the draw to facilitate refresh function later
        if (cBitmap != null) {
            canvas.drawBitmap(cBitmap, 0f, 0f, paint)
        }
    }
}