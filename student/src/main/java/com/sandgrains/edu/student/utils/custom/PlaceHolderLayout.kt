package com.sandgrains.edu.student.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sandgrains.edu.student.EduStudentApplication
import com.sandgrains.edu.student.R

class PlaceHolderLayout : FrameLayout, PlaceHolder {
    private val drawables = intArrayOf(0, 0, 0)
    private val texts = intArrayOf(0, 0, 0, 0)
    private lateinit var imageView: ImageView
    private lateinit var loading: ProgressBar
    private lateinit var textView: TextView
    private lateinit var retry: TextView
    private var bindViewHideType: Int = GONE

//    private var listener: SwipeRefreshLayout.OnRefreshListener? = null

    //PlaceHolderLayout 中的所有子view不一定会全部进行替换操作，所有使用PlaceHolderLayout时需把要进行替换的view添加进集合中
    private var bindViews: Array<View>? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        //加载布局
        inflate(context, R.layout.lay_placeholder, this)
        //绑定控件
        imageView = findViewById(R.id.image)
        loading = findViewById(R.id.loading)
        textView = findViewById(R.id.text)
        retry = findViewById(R.id.tv_retry)
        val typeArray =
            context.obtainStyledAttributes(attrs, R.styleable.PlaceHolderLayout, defStyle, 0)
        //错误提示图片
        drawables[0] =
            typeArray.getInt(R.styleable.PlaceHolderLayout_error_drawable, R.drawable.ic_error)
        //网络错误提示图片
        drawables[1] = typeArray.getInt(
            R.styleable.PlaceHolderLayout_error_net_drawable,
            R.drawable.ic_net_error
        )
        //空数据提示图片
        drawables[2] = typeArray.getInt(
            R.styleable.PlaceHolderLayout_empty_drawable,
            R.drawable.status_empty
        )

        //重新加载按钮的文字
        texts[0] =
            typeArray.getInt(R.styleable.PlaceHolderLayout_text_reload, R.string.reload_text)
        //网络错误提示文字
        texts[1] =
            typeArray.getInt(R.styleable.PlaceHolderLayout_text_net_error, R.string.net_error_text)
        //加载中的提示文字
        texts[2] =
            typeArray.getInt(R.styleable.PlaceHolderLayout_text_loading, R.string.loading_text)
        //空数据的提示文字
        texts[3] =
            typeArray.getInt(R.styleable.PlaceHolderLayout_text_empty, R.string.empty_text)

        bindViewHideType =
            typeArray.getInt(R.styleable.PlaceHolderLayout_bind_view_hide_type, GONE)
        //释放资源
        typeArray.recycle()
    }

    fun bindView(views: Array<View>) {
        this.bindViews = views
    }


    fun setOnRetryOnClickListener(listener: OnClickListener) {
        retry.setOnClickListener(listener)
    }

    override fun showEmptyView() {
        loading.visibility = GONE
        imageView.setImageResource(drawables[2])
        textView.setText(texts[3])
        textView.visibility = VISIBLE
        imageView.visibility = VISIBLE
        changeBindViewVisibility(INVISIBLE)
    }

    override fun showLoadView() {
        loading.visibility = VISIBLE
        textView.setText(texts[2])
        textView.visibility = VISIBLE
        imageView.visibility = GONE
        retry.visibility = GONE
        changeBindViewVisibility(INVISIBLE)
    }

    override fun showNetErrorView() {
        loading.visibility = GONE
        imageView.setImageResource(drawables[1])
        textView.setText(texts[1])
        textView.visibility = VISIBLE
        imageView.visibility = VISIBLE
        retry.visibility = VISIBLE
        changeBindViewVisibility(INVISIBLE)
    }

    override fun showErrorView(errorInfo: Int) {
        loading.visibility = GONE
        imageView.setImageResource(drawables[0])
        textView.setText(errorInfo)
        textView.visibility = VISIBLE
        imageView.visibility = VISIBLE
        retry.visibility = VISIBLE
        changeBindViewVisibility(INVISIBLE)
    }

    override fun showErrorView(errorInfo: String) {
        loading.visibility = GONE
        imageView.setImageResource(drawables[0])
        textView.text = errorInfo
        textView.visibility = VISIBLE
        imageView.visibility = VISIBLE
        retry.visibility = VISIBLE
        changeBindViewVisibility(INVISIBLE)
    }

    override fun loaded() {
        imageView.visibility = GONE
        retry.visibility = GONE
        loading.visibility = GONE
        textView.visibility = GONE
        changeBindViewVisibility(VISIBLE)
    }

    private fun changeBindViewVisibility(visible: Int) {
        bindViews?.let {
            var hideType = visible
            if (visible == GONE || visible == INVISIBLE) hideType = bindViewHideType
            for (v in it) {
                v.visibility = hideType
            }
        }
    }
}