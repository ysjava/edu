package com.sandgrains.edu.student.utils.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Scroller
import android.widget.TextView
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.utils.custom.video.TryWatchConfig
import com.sandgrains.edu.student.utils.logd
import com.sandgrains.edu.student.utils.loge
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.max
import kotlin.math.min

class PageTurningLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    //列数
    var columnCount: Int = 4

    //页数更改通知
    var pageIndexChange: ((Int) -> Unit)? = null
    var pageIndicator: PageIndicator? = null
    private var scroller: Scroller = Scroller(context)
    private var velocityTracker: VelocityTracker = VelocityTracker.obtain()
    private var lastXInIntercept = 0
    private var lastYInIntercept = 0
    private var lastX = 0
    private var lastY = 0
    private var childIndex = 0

    private var childWidth = 0
    var pageTurningConfig: PageTurningConfig? = null

    init {
        isClickable = true
        isFocusable = true
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageTurningLayout)
        columnCount = typedArray.getInt(R.styleable.PageTurningLayout_column_count, 4)
        typedArray.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val childMeasuredWidth = getChildAt(0)?.measuredWidth ?: 0
        childWidth = childMeasuredWidth
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else {
            val s = if (childCount % columnCount > 0) 1 else 0
            val rowNumber = childCount / columnCount + s
            val childHeight = getChildAt(0).measuredHeight
            setMeasuredDimension(childMeasuredWidth * columnCount, rowNumber * childHeight)
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0
        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            childView.layout(
                left,
                top,
                left + childView.measuredWidth,
                top + childView.measuredHeight
            )

            left += childView.measuredWidth
            //是否换行
            val hh = (index + 1) % columnCount == 0
            if (hh) {
                top += childView.measuredHeight
                left = 0
            }
        }
    }

    private var dispatchLastX = 0
    private var dispatchLastY = 0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {

                val deltaX = x - dispatchLastX
                val deltaY = y - dispatchLastY
                if (!b && ((scrollX >= childWidth && deltaX < 0) || (scrollX == 0 && deltaX > 0) || abs(
                        deltaY
                    ) > abs(deltaX))
                ) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        dispatchLastX = x
        dispatchLastY = y
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y
        var intercepted = false
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                    intercepted = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastXInIntercept
                val deltaY = y - lastYInIntercept
                intercepted = abs(deltaX) > abs(deltaY)
            }
            MotionEvent.ACTION_UP -> {
                intercepted = false
            }
        }

        lastXInIntercept = x.toInt()
        lastYInIntercept = y.toInt()
        lastX = x.toInt()
        lastY = y.toInt()
        return intercepted
    }

    var b = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        velocityTracker.addMovement(event)
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastX
                val deltaY = y - lastY
                val scale = String.format("%.2f", scrollX / childWidth.toFloat()).toFloat()
                updateIndicator(scale)
                val min = scrollX
                val max = childWidth - scrollX
                var newX = deltaX
                if (newX > min) newX = min
                if (scrollX - deltaX > childWidth) newX = -max
                if ((newX != -0 || newX != 0) && abs(deltaX) > abs(deltaY)) b = true
                scrollBy(-newX, 0)

            }
            MotionEvent.ACTION_UP -> {
                b = false
                velocityTracker.computeCurrentVelocity(1000)
                val vX = velocityTracker.xVelocity

                childIndex =
                    if (abs(vX) > 1000) {
                        if (vX > 0) childIndex - 1 else childIndex + 1
                    } else {
                        (scrollX + childWidth / 2) / childWidth
                    }

                childIndex = max(0, min(childIndex, columnCount - 1))

                //页数更改通知
                pageIndexChange?.let {
                    (childIndex)
                }

                val dx = childIndex * childWidth - scrollX

                smoothScrollBy(dx, 0)
                velocityTracker.clear()
            }
        }
        lastX = x
        lastY = y
        return true
    }

//fun setPageIndexChange(listener: (Int) -> Unit){
//
//}


    fun addViews(views: List<View>) {
        views.forEach {
            addViewInLayout(it, -1, it.layoutParams, true)
        }
        requestLayout()
        //invalidate()
    }

    private fun smoothScrollBy(dx: Int, dy: Int) {
        scroller.startScroll(scrollX, 0, dx, dy, 400)
        invalidate()
    }

    override fun computeScroll() {

        if (scroller.computeScrollOffset()) {
            val scale = String.format("%.2f", scrollX / childWidth.toFloat()).toFloat()
            updateIndicator(scale)
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        "onDetachedFromWindow".logd("PageTurningLayout")
        //velocityTracker.recycle()
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        "onAttachedToWindow".logd("PageTurningLayout")
        super.onAttachedToWindow()
    }

    private fun updateIndicator(scale: Float) {
        pageIndicator?.scale = scale
    }

    override fun onViewAdded(child: View) {
        "onViewAdded".logd("PageTurningLayout")
        super.onViewAdded(child)
        updateVisibleView(child)
        updateClickListener()
    }

    override fun onViewRemoved(child: View?) {
        "onViewRemoved".logd("PageTurningLayout")
        super.onViewRemoved(child)
        visibleViewList.remove(child)
//        visibleViewList.clear()
    }

    private fun updateVisibleView(child: View?) {
        if (child != null) visibleViewList.add(child)
    }

    private fun updateClickListener() {
        for (i in 0 until childCount) {
            getChildAt(i).setOnClickListener(onClickListener)
        }
    }

    fun scrollToIndex(index: Int, childWidth: Int = 0) {
        childIndex = index
        scrollTo(index * childWidth, 0)
        pageIndicator?.scale = index.toFloat()
    }

    private val visibleViewList: MutableList<View> = mutableListOf()
    private val onClickListener = OnClickListener {
        val pageTurningConfig = pageTurningConfig ?: return@OnClickListener
        val index = visibleViewList.indexOf(it)
        pageTurningConfig.onSelectViewChange(it, index)
    }

    class PageTurningConfig {
        var onSelectViewChange: (selectedView: View, index: Int) -> Unit =
            { _, _ -> }
    }

    fun configPageTurningConfig(config: PageTurningConfig.() -> Unit) {
        if (pageTurningConfig == null) {
            pageTurningConfig = PageTurningConfig()
        }

        pageTurningConfig?.config()
    }


    fun getCurIndex() = childIndex
    fun getChildWidth() = childWidth
}