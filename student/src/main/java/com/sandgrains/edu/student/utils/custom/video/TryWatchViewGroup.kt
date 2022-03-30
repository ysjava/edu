package com.sandgrains.edu.student.utils.custom.video

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.utils.custom.RoundAngleImageView
import com.sandgrains.edu.student.utils.loge
import com.shuyu.gsyvideoplayer.utils.CommonUtil.dip2px

private const val TAG = "TryWatchViewGroup"

class TryWatchViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    var tryWatchConfig: TryWatchConfig? = null
    private val paint = Paint()
    private val colorDrawable = ColorDrawable(resources.getColor(R.color.tran_gray, null))

    //间隔 20dp
    private val specSize = dip2px(context, 20f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
            return
        }
        val childView = getChildAt(0)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(widthSpecSize, childView.measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        if (childCount < 1) return
        val childWidth = getChildAt(0).measuredWidth
        var startPosition = when (childCount) {
            1 -> {
                measuredWidth / 2 - childWidth / 2
            }
            2 -> {
                measuredWidth / 2 - childWidth - specSize / 2
            }
            3 -> {
                measuredWidth / 2 - childWidth - specSize - childWidth / 2
            }
            else -> -1
        }
        this.startPosition = startPosition
        if (childCount > 3)
            throw IllegalArgumentException("TryWatchViewGroup children should not exceed 3")


        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val left = startPosition
            val right = startPosition + child.measuredWidth

            child.layout(left, 0, right, child.measuredHeight)
            startPosition += (specSize + child.measuredHeight)
        }
    }

    fun configTryWatchConfig(config: TryWatchConfig.() -> Unit) {
        if (tryWatchConfig == null) {
            tryWatchConfig = TryWatchConfig()
        }

        tryWatchConfig?.config()
    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
        "onViewAdded".loge()
        updateVisibleView(child)
        updateClickListener()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (childCount == 0) return
        var child = getChildAt(0) as LinearLayout
        var imageView = child.getChildAt(0) as RoundAngleImageView
        val title = child.getChildAt(1) as TextView
        beforeText = title
        beforeImageView = imageView
        title.setTextColor(Color.RED)

        for (i in 0 until childCount) {
            child = getChildAt(i) as LinearLayout
            imageView = child.getChildAt(0) as RoundAngleImageView
            if (i > 0)
                imageView.foreground = colorDrawable
        }


    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (childCount == 0) return
        paint.color = Color.BLACK
        val child = getChildAt(0) as LinearLayout
        val imageView = child.getChildAt(0)

        val left = startPosition + currentPosition * (specSize + child.measuredHeight)
        paint.strokeWidth = 6f
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(
            left.toFloat(),
            3f,
            (left + child.measuredWidth).toFloat(),
            imageView.measuredHeight.toFloat() - 3,
            22f,
            22f,
            paint
        )
    }

    private fun updateVisibleView(child: View?) {
        if (child != null) visibleViewList.add(child)
    }

    private fun updateClickListener() {
        for (i in 0 until childCount) {
            getChildAt(i).setOnClickListener(onClickListener)
        }
    }

    private var beforeText: TextView? = null
    private var beforeImageView: RoundAngleImageView? = null
    private val visibleViewList: MutableList<View> = mutableListOf()
    private val onClickListener = OnClickListener {
        beforeText?.setTextColor(Color.WHITE)
        beforeImageView?.foreground = colorDrawable

        val tryWatchConfig = tryWatchConfig ?: return@OnClickListener
        it as LinearLayout
        val imageView = it.getChildAt(0) as RoundAngleImageView
        val title = it.getChildAt(1) as TextView
        title.setTextColor(Color.RED)
        imageView.foreground = null

        val index = visibleViewList.indexOf(it)
        tryWatchConfig.onSelectViewChange(it, it, index)
        currentPosition = index
        beforeText = title
        beforeImageView = imageView
        invalidate()
    }

    private var currentPosition = 0
    private var startPosition = 0

}