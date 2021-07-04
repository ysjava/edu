package com.sandgrains.edu.student.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import cn.bingoogolapple.bgabanner.BGABanner

class NestedBGABanner @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BGABanner(context, attrs, defStyleAttr) {
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }
}