package com.sandgrains.edu.learnmodel

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.NestedScrollingChild2

class NestedCoordinatorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    :ViewGroup(context,attrs, defStyleAttr),NestedScrollingChild2 {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")

    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun stopNestedScroll(type: Int) {
        TODO("Not yet implemented")
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?, type: Int): Boolean {
        TODO("Not yet implemented")
    }
}