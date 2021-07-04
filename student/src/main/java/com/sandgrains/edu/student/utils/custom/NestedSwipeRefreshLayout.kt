package com.sandgrains.edu.student.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sandgrains.edu.student.R

class NestedSwipeRefreshLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeRefreshLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}