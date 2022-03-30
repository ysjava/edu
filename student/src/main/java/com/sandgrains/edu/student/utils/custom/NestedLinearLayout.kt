package com.sandgrains.edu.student.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.utils.loge
import java.text.DecimalFormat
import kotlin.math.abs

private const val TAG = "NestedLinearLayout"

class NestedLinearLayout @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : LinearLayout(context, attributeSet, defStyleAttr), NestedScrollingParent2 {
    private lateinit var topView: View
    private lateinit var viewPager2: ViewPager2
    private lateinit var layTab: LinearLayout

    //    private lateinit var recyclerView: RecyclerView
    private var scroller: Scroller
    private var velocityTracker: VelocityTracker
    private var touchSlop: Int
    private var minimumVelocity: Int
    private var maxFlingVelocity: Int
    private var target: View? = null
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutParams = viewPager2.layoutParams
        layoutParams.height = measuredHeight - layTab.measuredHeight
        viewPager2.layoutParams = layoutParams
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    init {
        isClickable = true
        isFocusable = true
        scroller = Scroller(context, LinearInterpolator(), false)
        velocityTracker = VelocityTracker.obtain()
        val vc = ViewConfiguration.get(context)
        touchSlop = vc.scaledTouchSlop
        minimumVelocity = vc.scaledMinimumFlingVelocity
        maxFlingVelocity = vc.scaledMaximumFlingVelocity

    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        this.target = target
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {}

    override fun onStopNestedScroll(target: View, type: Int) {}

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {}

    //手指下拉就是上滚动，反之下滚动
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val scrollY = scrollY
        val topViewHeight = topView.height
        if (scrollY <= 0 && dy < 0) return
        //当recyclerview还可以向上滚动并且是向上滚动就让recyclerview消费
        if (target.canScrollVertically(-1) && dy < 0) return
        //顶部View是否处于显示状态
        val showTop = scrollY < topViewHeight

        if ((showTop) || (!target.canScrollVertically(-1) && dy < 0)) {

            val min = -scrollY
            val max = topViewHeight - scrollY
            var consumedY = dy
//            consumedY = dy.coerceAtMost(max)
//            consumedY = dy.coerceAtLeast(min)
            if (dy > max) consumedY = max
            if (dy < min) consumedY = min

            scrollBy(0, consumedY)
            consumed[1] = consumedY
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = findViewById(R.id.topView)
        viewPager2 = findViewById(R.id.viewpager1)
        layTab = findViewById(R.id.lay_tab)
    }


    private fun isTouchPointInView(view: View, touchX: Int, touchY: Int): Boolean {
        val intArray = IntArray(2)
        view.getLocationOnScreen(intArray)
        val left = intArray[0]
        val top = intArray[1]
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight - 173
        Log.e(TAG, "isTouchPointInView  view:${view.measuredHeight}   left:${left}   right:${right}    top:${top}   bottom:${bottom}  touchX:${touchX}   touchRawY:${touchY}")
        return touchX in left..right && (touchY in top..bottom)
    }

    //当事件位置在recyclerview中就不拦截
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (target != null && target is RecyclerView) {
                    (target as RecyclerView).stopScroll()
                }
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }

            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = ev.x - lastXInIntercept
                val deltaY = ev.y - lastYInIntercept
                if (isTouchPointInView(topView, ev.x.toInt(), ev.y.toInt())&& (abs(deltaX)>5|| abs(deltaY)>5)) {
                    intercepted = true
                }
            }
        }
        lastXInIntercept = ev.x.toInt()
        lastYInIntercept = ev.y.toInt()
        lastX = ev.x.toInt()
        lastY = ev.y.toInt()
        return intercepted
    }

    private var lastXInIntercept = 0
    private var lastYInIntercept = 0
    // 分别记录上次滑动的坐标
    private var lastX = 0
    private var lastY = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        velocityTracker.addMovement(event)
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                //如果可以上滑，那么不允许topView部分的拖动
                val isPull = target?.canScrollVertically(-1) ?: false
                if (isPull) {
                    return false
                }


                var deltaY = y - lastY
                val topViewHeight = topView.height
                val min = -scrollY
                val max = topViewHeight - scrollY

                if (-deltaY < min) deltaY = -min
                if (-deltaY > max) deltaY = -max

                scrollBy(0, -deltaY)
            }
            MotionEvent.ACTION_UP -> {
                val isPull = target?.canScrollVertically(-1) ?: false
                if (isPull) {
                    return false
                }
                velocityTracker.computeCurrentVelocity(1000, maxFlingVelocity.toFloat())
                val yVelocity = velocityTracker.yVelocity.toInt()
                if (abs(yVelocity) > minimumVelocity) {
                    doFling(yVelocity)
                    velocityTracker.clear()
                }
            }
        }

        lastY = y
        return true
    }

    private fun doFling(yVelocity: Int) {
        scroller.fling(0, scrollY, 0, -(yVelocity), 0, 0, 0, topView.height)
        val duration = handleDuration(scroller.duration)
        scroller.extendDuration(duration)
        invalidate()
    }

    /**
     * Scroller fling时间随速度越大时间越长，当可滚动距离有限时，fling速度快时间却长，滚动速度就慢，看起来就是快滑反而比慢滑滚动慢
     * 对时间进行转换，最快50，最慢700，随着时间越大，转换后的时间越小，最低到50
     *
     * */
    private fun handleDuration(duration: Int): Int {
        if (duration < 700) return duration
        if (duration > 1500) return 10
        return 700 - (0.8125 * (duration - 700)).toInt()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }
}