package com.sandgrains.edu.student.ui.home.java

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.hi.dhl.binding.viewbind
import com.lxj.xpopup.XPopup
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentCourseJava2Binding
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.ui.course.introduce.CourseIntroduceActivity
import com.sandgrains.edu.student.ui.home.JavaRecommend
import com.sandgrains.edu.student.ui.video.PlayActivity
import com.sandgrains.edu.student.utils.base.DiffUiDataCallback
import com.sandgrains.edu.student.utils.base.ItemClickCallback
import com.sandgrains.edu.student.utils.base.NetWorkError
import com.sandgrains.edu.student.utils.base.ServiceError

import com.sandgrains.edu.student.utils.custom.*
import com.sandgrains.edu.student.utils.logd
import com.sandgrains.edu.student.utils.loge
import kotlinx.coroutines.flow.catch
import kotlin.random.Random


class CourseJavaTestActivity : AppCompatActivity(R.layout.fragment_course_java2), ItemClickCallback<JavaRecommend> {
    private val binding: FragmentCourseJava2Binding by viewbind()
    private val viewModel by lazy { ViewModelProvider(this).get(CourseJavaViewModel::class.java) }

    private val adapter = Adapter(mutableListOf())
    private val loadingDialog by lazy { XPopup.Builder(this).asLoading() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

//        lifecycleScope.launch {
//            viewModel.getCoursesByCustom()
//                    .collect {
//                        //关闭刷新
//
//                        //设置数据
//                        adapter.submitData(it)
//                    }
//        }


        initWidget()
        initData()
        initObserve()
    }


    data class Course(
            val name: String,
            val id: String,
            val imgUrl: String,
            val type: Int,//1.java 2......
            val desc: String,
            val studyNumber: Int = 999,
            val price: Int = 0,
//        @PrimaryKey(autoGenerate = true)
            val cid: Int = 0,
    ) : DiffUiDataCallback.UiDataDiffer<Course> {
        override fun isSame(old: Course): Boolean {
            return this === old
        }

        override fun isUiContentSame(old: Course): Boolean {
            return this.name == old.name
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initObserve() {

        viewModel.loadData2.observe(this, { result ->
            binding.swipeRefresh.isRefreshing = false
            val data = result.getOrNull()
            if (data != null) {
                val oldList = adapter.dataList
                // 进行数据对比

                val callback: DiffUtil.Callback = DiffUiDataCallback(oldList, data)
                val result2 = DiffUtil.calculateDiff(callback)
                adapter.refreshByDiff(result2, data)
            } else {
                result.exceptionOrNull()?.let {
                    when (it) {
                        is ServiceError -> {
                            "ServiceError ${it}".loge("TESGTASD")
                        }
                        else -> {
                            "ELse  ${it}".loge("TESGTASD")
                        }
                    }
                }
            }

        })


        viewModel.checkCourseIsPay.observe(this, { result ->
            val data = result.getOrNull()
            if (loadingDialog.isShow)
                loadingDialog.dismiss()

            if (data != null) {
                val target = if (data.second == "PAID") {
                    //课程已经支付， 跳转到课程播放页面
                    PlayActivity::class.java
                } else {
                    //未支付 跳转到购买详情页面
                    CourseIntroduceActivity::class.java
                }

                val intent = Intent(this, target)
                intent.putExtra("course_id", data.first)
                startActivity(intent)
            }
        })
    }

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private fun initWidget() {
        swipeRefresh = binding.swipeRefresh

        binding.placeHolder.setOnRetryOnClickListener {
//            adapter.retry()
        }

        swipeRefresh.setOnRefreshListener {
//            adapter.curIndex = 0
//            adapter.refresh()
            viewModel.loadData2()
        }

        viewModel.requestException.observe(this, {
            when (it) {
                is NetWorkError -> {

                }
                is ServiceError -> {

                }
            }
        })

    }

    private fun initData() {

        //获取数据
        viewModel.loadData2()
    }

    override fun onItemClick(data: JavaRecommend, position: Int) {
        //loadingDialog.show()
        //viewModel.checkCourseIsPay(data.id)
        data as Course
        val target = if (data.price <= 0) {
            //课程已经支付， 跳转到课程播放页面
            PlayActivity::class.java
        } else {
            //未支付 跳转到购买详情页面
            CourseIntroduceActivity::class.java
        }

        val intent = Intent(this, target)
        intent.putExtra("course_id", data.id)
        startActivity(intent)
        //loadingDialog.dismiss()
//                val intent = Intent(fragment.activity, PlayActivity::class.java)
//                intent.putExtra("course_id","课程id")
//                fragment.startActivity(intent)
    }

    inner class Adapter(val dataList: MutableList<Course>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val img: RoundAngleImageView = itemView.findViewById(R.id.iv_img)
            val name: TextView = itemView.findViewById(R.id.tv_course_name)
            val desc: TextView = itemView.findViewById(R.id.tv_course_desc)
            val price: TextView = itemView.findViewById(R.id.tv_course_price)
            val studyNumber: TextView = itemView.findViewById(R.id.tv_course_study_number)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            "onBindViewHolder: $holder".logd("KGBKJWGEW")
            val data = dataList[position]
            Glide.with(this@CourseJavaTestActivity).load(data.imgUrl).placeholder(R.color.gray).into(holder.img)
            holder.name.text = data.name
            holder.desc.text = data.desc
            holder.price.text = if (data.price == 0) "免费" else "¥${data.price}"
            holder.studyNumber.text = "${data.studyNumber} 人学习"
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        fun refreshByDiff(result2: DiffUtil.DiffResult, data: MutableList<Course>) {
            dataList.clear()
            dataList.addAll(data)

            result2.dispatchUpdatesTo(this)
        }
    }
}