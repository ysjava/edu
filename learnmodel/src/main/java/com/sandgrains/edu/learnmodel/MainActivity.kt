package com.sandgrains.edu.learnmodel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sandgrains.edu.learnmodel.paging3.DiffUiDataCallback
import com.sandgrains.edu.learnmodel.paging3.MyViewModel
import com.sandgrains.edu.learnmodel.paging3.model.Repo

fun setStatusBarColor(window: Window, @ColorRes colorId: Int) {
    window.statusBarColor = window.context.resources.getColor(colorId, null)
}

/**
 * @param visibility
 * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //状态栏隐藏
 * View.SYSTEM_UI_FLAG_VISIBLE //状态栏显示
 * View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //状态栏字体颜色为黑色
 * View.SYSTEM_UI_FLAG_LAYOUT_STABLE //状态栏字体颜色跟随系统 好像都是白的
 * */
fun setStatusBarVisibility(window: Window, visibility: Int) {
    window.decorView.systemUiVisibility = visibility
}

class MainActivity : AppCompatActivity() {

    //    private lateinit var et: EditText
    private lateinit var tv: TextView
    private val viewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
private val handler = Handler(mainLooper)
    //    private val viewModel by lazy { ViewModelProvider(this).get(CourseJavaViewModel::class.java) }
    private val adapter = Adapter(mutableListOf(Main(1, "name1")))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        window.statusBarColor = Color.GREEN
        var b = false
//        findViewById<View>(R.id.view).setOnClickListener {
//            it.scrollBy(-10,-10)
//        }
//
//        val pt = findViewById<PageTurningLayout>(R.id.lay_page_turning)
//        val pi = findViewById<PageIndicator>(R.id.page_indicator)
//        pt.pageIndicator = pi

//        lifecycleScope.launch {
//            viewModel.getData().collect {
//                //if (refresh.isRefreshing) refresh.isRefreshing = false
//                adapter.submitData(it)
//            }
//        }


        findViewById<Button>(R.id.buttonPanel).setOnClickListener {
//            adapter.refresh()
            val oldList = adapter.dataList
            val newList = listOf<Main>(Main(1, "name" + (120..999).random()))

            // 进行数据对比
            val callback: DiffUtil.Callback = DiffUiDataCallback(oldList, newList)
            val result = DiffUtil.calculateDiff(callback)
            handle(result, newList)
//            adapter.notifyDataSetChanged()
//            if (b) {
////                window.statusBarColor = Color.GREEN
////                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                setStatusBarColor(window, R.color.teal_200)
//                setStatusBarVisibility(window, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//            } else {
////                window.statusBarColor = Color.RED
////                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//                setStatusBarColor(window, R.color.teal_700)
//                setStatusBarVisibility(window, View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//            }
//            b = !b
        }
//        findViewById<Button>(R.id.button).setOnClickListener {
//            startActivity(Intent(this, SecondActivity::class.java))
//        }
//        SystemClock.sleep(30*10000)
//        var x = 200f
//        var v = 1.5f
//findViewById<Practice01ClipRectView>(R.id.circleView).setOnClickListener {
//
//    it as Practice01ClipRectView
//    it.setTest()
//
//}
//        et = findViewById(R.id.tv_test)
//        val bt = findViewById<Button>(R.id.bt_test)
//        val stickyNavLayout = findViewById<StickyLayout>(R.id.stickyNavLayout)
//        val recyclerView = findViewById<RecyclerView>(R.id.rv)
//        val vAlways = findViewById<View>(R.id.v_always)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val dataArray = arrayListOf<String>()
//        for (i in 0..20) {
//            dataArray.add("第$i 项数据")
//        }


//        listView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
//            Toast.makeText(this@DemoActivity_1, "click item",
//                    Toast.LENGTH_SHORT).show()
//        })

//        recyclerView.adapter = Adapter(dataArray)
//        stickyNavLayout.setAlways(vAlways, vAlways.paddingTop)
//        vAlways.post {
//            stickyNavLayout.setAlways(vAlways, vAlways.paddingTop)
//        }
//        val html ="ssad    <img src='https://img-blog.csdn.net/20140707162657281'/>"+
//                "又是一张 <img src='https://img1.baidu.com/it/u=2192265457,2884791613&fm=26&fmt=auto&gp=0.jpg'/>"
////        Html.fromHtml(str, Html.ImageGetter { source -> Log.e("KGF", "aa") }, null)
//        tv.setHtmlFromString(html)
//        bt.setOnClickListener {
//            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//            intent.addCategory(Intent.CATEGORY_OPENABLE)
//            intent.type = "image/*"
//
//            startActivityForResult(intent, 1)
//        }

    }

    private fun handle(result: DiffUtil.DiffResult, newList: List<Main>) {
        adapter.dataList.clear()
        adapter.dataList.addAll(newList)

        result.dispatchUpdatesTo(adapter)
    }

    override fun onResume() {
        super.onResume()
//        tv.text = " left: ${tv.left},\n right: ${tv.right},\n top: ${tv.top},\n bottom: ${tv.bottom}"
    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            1 -> {
//                if (resultCode == Activity.RESULT_OK && data != null) {
//
//                    data.data?.let {
//
//                        var bitmap = getBitmapFromUri(it)!!
//
//                        bitmap = calculateBitmapSize(bitmap)
////                        bitmap = setImgSize(bitmap,1)!!
//                        val imageSpan = ImageSpan(this, bitmap)
//                        val tempUrl =
//                            "<img src=\"" + "https://img-blog.csdn.net/20140707162657281" + "\" />"
//                        val spannableString = SpannableString(tempUrl)
//                        spannableString.setSpan(
//                            imageSpan,
//                            0,
//                            tempUrl.length,
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                        )
//
//                        // 将选择的图片追加到EditText中光标所在位置
//                        val index: Int = et.selectionStart
//// 获取光标所在位置
//                        val edit_text: Editable = et.editableText
//                        if (index < 0 || index >= edit_text.length) {
//                            edit_text.append(spannableString)
//                        } else {
//                            edit_text.insert(index, spannableString)
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun getBitmapFromUri(it: Uri) = contentResolver.openFileDescriptor(it, "r")?.use {
//        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
//    }
//
//
//    private fun calculateBitmapSize(bitmap: Bitmap): Bitmap {
//        val width = bitmap.width
//        val height = bitmap.height
//        val etWidth = et.width
//
//
//        //bitmap缩放的比例
//        val scale: Float
//        scale =
//                if (width > height) {
//                    //当宽度大于等于编辑框宽度时就设置为编辑框高度,否则就保持不变
//                    val newWidth = if (width >= etWidth) etWidth else width
//                    //计算newWidth相对与width的缩放比例
//                    newWidth.toFloat() / width
//                } else {
//                    //并且大于等于屏幕高度的1/3, 就设置为屏幕高度的1/3,否则就保持不变
//                    val oneInThirdScreenHeight = DisplayUtil.getScreenHeight(this) / 3
//                    val newHeight = if (height >= oneInThirdScreenHeight) oneInThirdScreenHeight else height
//                    //计算newHeight相对与height的缩放比例
//                    newHeight.toFloat() / height
//                }
//
//        val matrix = Matrix()
//        matrix.setScale(scale, scale)
//
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
//    }
    data class Main(val id: Int, val name: String) : DiffUiDataCallback.UiDataDiffer<Main> {
        override fun isSame(old: Main): Boolean {
            return this == old || this.id == old.id
        }

        override fun isUiContentSame(old: Main): Boolean {
            return this.name == old.name
        }
    }

    class Adapter(val dataList: MutableList<Main>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val text = itemView.findViewById<TextView>(R.id.name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.content_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Log.d("ASNFasd","onBindViewHolder  $holder")
            holder.text.text = dataList[position].name
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }

    class PAdapter() : PagingDataAdapter<Repo, PAdapter.ViewHolder>(diffCallback) {

        companion object {
            val diffCallback = object : DiffUtil.ItemCallback<Repo>() {
                override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                    return oldItem == newItem
                }

            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val text = itemView.findViewById<TextView>(R.id.name)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = getItem(position)?.name ?: "居然是空的!"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_list_item, parent, false))
        }

//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.content_list_item, parent, false)
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.text.text = dataList[position]
//        }
//
//        override fun getItemCount(): Int {
//            return dataList.size
//        }
    }
}