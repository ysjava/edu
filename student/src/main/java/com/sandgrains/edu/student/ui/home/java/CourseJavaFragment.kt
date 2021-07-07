package com.sandgrains.edu.student.ui.home.java

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.angcyo.tablayout.DslTabLayout
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentCourseJavaBinding
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.model.course.JavaCourseModel
import com.sandgrains.edu.student.ui.video.PlayActivity
import com.sandgrains.edu.student.utils.custom.NestedBGABanner
import com.sandgrains.edu.student.utils.custom.PlaceHolderLayout

class CourseJavaFragment : Fragment(R.layout.fragment_course_java) {
    private val binding: FragmentCourseJavaBinding by viewbind()
    private val viewModel by lazy { ViewModelProvider(this).get(CourseJavaViewModel::class.java) }
    private lateinit var placeHolder: PlaceHolderLayout
    private lateinit var placeHolder2: PlaceHolderLayout
    private lateinit var banner: NestedBGABanner
    private lateinit var recyclerView: RecyclerView
    private lateinit var laybind: LinearLayout
    private lateinit var tableLayout: DslTabLayout
    private lateinit var tableLayout2: DslTabLayout
    private lateinit var adapter: CourseAdapter
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var scrollview: NestedScrollView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.banner = binding.banner
        this.placeHolder = binding.layPlaceHolder
        this.placeHolder2 = binding.layPlaceHolder2
        this.recyclerView = binding.recycler
        this.tableLayout = binding.tabLayout
        this.tableLayout2 = binding.tabLayout2
        this.laybind = binding.layBind
        this.refresh = binding.refresh
        this.scrollview = binding.scrollview
        initWidget()
        initData()
        initObserve()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CourseAdapter(this)
        recyclerView.adapter = adapter
    }

    var a = 0
    private fun initObserve() {
        //加载数据
        viewModel.loadData.observe(viewLifecycleOwner, { result ->
            if (refresh.isRefreshing) refresh.isRefreshing = false

            val data = result.getOrNull()
            if (data != null) {
                val viewList = arrayListOf<View>()
                val modelList = mutableListOf<JavaCourseModel.RecommendModel>()
                for (i in data.recommendCourseList) {
                    viewList.add(layoutInflater.inflate(R.layout.item_page, null, false))
                    modelList.add(i)
                }
//                banner.setData(viewList, modelList, null)
                adapter.setData(data.courseList)
                if (modelList.size > 0) placeHolder2.loaded() else placeHolder2.showEmptyView()
            } else {
                placeHolder2.showErrorView("E/ent:pushservic: Unknown bits set in runtime_flags: 0x28000,E/ent:pushservic: Unknown bits set in runtime_flags: 0x28000")
            }
        })

        //课程排序查询
        viewModel.getCourseSortedList.observe(viewLifecycleOwner, { result ->
            val list = mutableListOf<Course>()
            for (i in 1..18) {
                list.add(Course("$a 课程$i", i.toString(), "ximgutl", 1, "tryurl", "描述巴拉巴拉", listOf()))
            }
            adapter.setData(list)
            placeHolder.loaded()
//            val data = result.getOrNull()
//            if (data != null) {
//                adapter.setData(data)
//                if (data.isNotEmpty()) placeHolder.loaded() else placeHolder.showEmptyView()
//            } else {
//                placeHolder.showErrorView("E/ent:pushservic: Unknown bits set in runtime_flags: 0x28000,E/ent:pushservic: Unknown bits set in runtime_flags: 0x28000")
//            }
        })

    }

    private lateinit var currentItemView: TextView

    private fun initWidget() {
        placeHolder2.apply {
            bindView(arrayOf(laybind))

            setOnRetryOnClickListener {
                showLoadView()
                viewModel.loadData()
            }
        }

        placeHolder.apply {
            bindView(arrayOf(recyclerView))

            setOnRetryOnClickListener {
                showLoadView()
                viewModel.getCourseSortedList(currentItemView.text.toString())
            }
        }

        banner.setAdapter { banner, itemView, model, position ->
        }
        banner.setDelegate { _, _, model, _ ->

        }

        refresh.setOnRefreshListener {
            viewModel.loadData()
        }

        tableLayout.configTabLayoutConfig {
            onSelectViewChange = { _, selectViewList, reselect, _ ->
                val selectView = selectViewList[0] as TextView
                currentItemView = selectView
                if (!reselect) {
                    a += 1
                    placeHolder.showLoadView()
                    viewModel.getCourseSortedList(selectView.text.toString())
                }
                tableLayout2.setCurrentItem(tableLayout.currentItemIndex)
            }
        }

        tableLayout2.configTabLayoutConfig {
            onSelectViewChange = { _, _, _, _ ->
                tableLayout.setCurrentItem(tableLayout2.currentItemIndex)
            }
        }

        //记录上一次设置 防止滑动时多次重复设置 visibility
        var lastVisibility = View.GONE
        //滚动监听
        scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //根据banner的位置对悬挂View tableLayout2的可见度设置
            val banner = v.findViewById<NestedBGABanner>(R.id.banner) as View
            val rect = Rect()
            banner.getGlobalVisibleRect(rect)

            if (rect.bottom >= 0 && lastVisibility != View.GONE) {
                tableLayout2.visibility = View.GONE
                lastVisibility = View.GONE

            } else if (rect.bottom < 0 && lastVisibility != View.VISIBLE) {
                tableLayout2.visibility = View.VISIBLE
                lastVisibility = View.VISIBLE
            }

        })
    }

    private fun initData() {
        //this.placeHolder2.showLoadView()
        //获取数据
        //viewModel.loadData()

        val list = arrayListOf<View>()
        val colorResList = listOf(R.color.test_color1, R.color.test_color2, R.color.test_color3)
        for (i in 0..2) {
            val view = layoutInflater.inflate(R.layout.item_page, null, false)
            val image = view.findViewById<ImageView>(R.id.image)
            image.setImageResource(colorResList[i])
            list.add(view)
        }
        binding.banner.setData(list)
    }

    class CourseAdapter(
            private val fragment: CourseJavaFragment,
            private val dataList: MutableList<Course> = arrayListOf()
    ) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val img: ImageView = itemView.findViewById(R.id.iv_img)
            val name: TextView = itemView.findViewById(R.id.tv_course_name)
            val desc: TextView = itemView.findViewById(R.id.tv_course_desc)
            val price: TextView = itemView.findViewById(R.id.tv_course_price)
            val studyNumber: TextView = itemView.findViewById(R.id.tv_course_study_number)
        }

        fun setData(dataList: List<Course>) {
            this.dataList.clear()
            this.dataList.addAll(dataList)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = dataList[position]
            holder.apply {
                Glide.with(fragment).load(data.imgUrl).placeholder(R.color.gray).into(img)
                name.text = data.name
                desc.text = data.desc
                price.text = if (data.price == 0) "免费" else "¥${data.price}"
                studyNumber.text = "${data.studyNumber} 人学习"
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(fragment.activity, PlayActivity::class.java)
                intent.putExtra("course_id","课程id")
                fragment.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }

}