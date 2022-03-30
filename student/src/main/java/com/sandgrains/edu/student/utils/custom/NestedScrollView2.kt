package com.sandgrains.edu.student.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sandgrains.edu.student.R

class NestedScrollView2 @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : LinearLayout(context, attributeSet, defStyleAttr) {
    private lateinit var rv: PlaceHolderLayout
    private lateinit var banner: NestedBGABanner
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val layoutParams = rv.layoutParams
        layoutParams.height = measuredHeight
        rv.layoutParams = layoutParams
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        rv = findViewById(R.id.lay_place_holder)
        banner = findViewById(R.id.banner)

    }
}