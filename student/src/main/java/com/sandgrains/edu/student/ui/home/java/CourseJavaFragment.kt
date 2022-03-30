package com.sandgrains.edu.student.ui.home.java

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hi.dhl.binding.viewbind
import com.lxj.xpopup.XPopup
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentCourseJava2Binding
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.ui.course.introduce.CourseIntroduceActivity
import com.sandgrains.edu.student.ui.video.PlayActivity
import com.sandgrains.edu.student.utils.base.DiffUiDataCallback
import com.sandgrains.edu.student.utils.base.ItemClickCallback
import com.sandgrains.edu.student.utils.base.NetWorkError
import com.sandgrains.edu.student.utils.base.ServiceError
import com.sandgrains.edu.student.utils.custom.*


class CourseJavaFragment : Fragment(R.layout.fragment_course_java2), ItemClickCallback<IJavaCourseAdapter> {
    private val binding: FragmentCourseJava2Binding by viewbind()
    private val viewModel by lazy { ViewModelProvider(this).get(CourseJavaViewModel::class.java) }

    private val adapter = JavaCourseAdapter(this, this)
    private val loadingDialog by lazy { XPopup.Builder(context).asLoading() }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
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
//
//
//        recyclerView.adapter = adapter.withLoadStateFooter(FooterAdapter { adapter.retry() })
    }

    var a = 0

    @SuppressLint("SetTextI18n")
    private fun initObserve() {

        //课程排序查询
        viewModel.getCourseSortedList.observe(viewLifecycleOwner, { result ->
            val list = mutableListOf<Course>()
            for (i in 1..18) {
                list.add(Course("t$a 课程$i", i.toString(), "ximgutl", 1, null, "描述巴拉巴拉", listOf()))
            }
//            adapter.setData(list)
            //placeHolder.loaded()
//            val data = result.getOrNull()
//            if (data != null) {
//                adapter.setData(data)
//                if (data.isNotEmpty()) placeHolder.loaded() else placeHolder.showEmptyView()
//            } else {
//                placeHolder.showErrorView("E/ent:pushservic: Unknown bits set in runtime_flags: 0x28000,E/ent:pushservic: Unknown bits set in runtime_flags: 0x28000")
//            }
        })

        viewModel.checkCourseIsPay.observe(viewLifecycleOwner, { result ->
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

                val intent = Intent(activity, target)
                intent.putExtra("course_id", data.first)
                startActivity(intent)
            }
        })

        viewModel.loadData.observe(viewLifecycleOwner, { result ->
            binding.swipeRefresh.isRefreshing = false
            val data = result.getOrNull()
            if (data != null) {
                val oldList = adapter.dataList
                // 进行数据对比

                val callback: DiffUtil.Callback = DiffUiDataCallback(oldList, data)
                val result2 = DiffUtil.calculateDiff(callback)
                adapter.refreshByDiff(result2, data)

//                adapter.updateDataList(data)
            }
        })
    }

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private fun initWidget() {
        swipeRefresh = binding.swipeRefresh

        binding.placeHolder.setOnRetryOnClickListener {
            //adapter.retry()
            viewModel.loadData()
        }

        swipeRefresh.setOnRefreshListener {
//            adapter.curIndex = 0
//            adapter.refresh()
            viewModel.loadData()
        }

//        adapter.addLoadStateListener {
//            when (it.refresh) {
//                is LoadState.NotLoading -> {
//                    swipeRefresh.isRefreshing = false
//                }
//                is LoadState.Error -> {
//                    swipeRefresh.isRefreshing = false
//                }
//                is LoadState.Loading -> {
//                }
//            }
//        }

        viewModel.requestException.observe(viewLifecycleOwner, {
            when (it) {
                is NetWorkError -> {

                }
                is ServiceError -> {

                }
            }
        })

//        adapter.addLoadStateListener {
//            placeHolder.apply {
//                when (it.refresh) {
//                    is LoadState.NotLoading -> {
//                        loaded()
//                    }
//                    is LoadState.Error -> {
//                        val error = it.refresh as LoadState.Error
//                        val ce = (error.error as? ServiceError) ?: NetWorkError()
//
//                        if (ce is ServiceError)
//                            showErrorView("服务器错误码：${ce.code}, 信息： ${ce.msg}")
//                        else
//                            showNetErrorView()
//                    }
//                    is LoadState.Loading -> {
//                        showLoadView()
//                    }
//                }
//            }
//
//        }

//        placeHolder2.apply {
//            bindView(arrayOf(laybind))
//
//            setOnRetryOnClickListener {
//                showLoadView()
//                viewModel.loadData()
//            }
//        }
//
//        placeHolder.apply {
//            bindView(arrayOf(recyclerView))
//
//            setOnRetryOnClickListener {
//                showLoadView()
//                lifecycleScope.launch {
//                    viewModel.getCoursesByCustom().collect {
//                        adapter.submitData(it)
//                    }
//                }
//            }
//        }

//        refresh.setOnRefreshListener {
//            //viewModel.loadData()
//            adapter.refresh()
////            lifecycleScope.launch {
////                viewModel.getCoursesByCustom().collect {
////                    if (refresh.isRefreshing) refresh.isRefreshing = false
////                    adapter.submitData(it)
////                }
////            }
//        }

    }

    private fun initData() {
        //获取数据
        viewModel.loadData()
    }

    override fun onItemClick(data: IJavaCourseAdapter, position: Int) {
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

        val intent = Intent(activity, target)
        intent.putExtra("course_id", data.id)
        startActivity(intent)
        //loadingDialog.dismiss()
//                val intent = Intent(fragment.activity, PlayActivity::class.java)
//                intent.putExtra("course_id","课程id")
//                fragment.startActivity(intent)
    }

//    class Adapter(): BaseRecyclerAdapter<>

}