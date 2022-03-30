package com.sandgrains.edu.student.ui.course.introduce

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.hi.dhl.binding.viewbind
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.ActivityCourseIntroduceBinding
import com.sandgrains.edu.student.logic.Repository
import com.sandgrains.edu.student.model.Course
//import com.sandgrains.edu.student.databinding.Test3Binding
import com.sandgrains.edu.student.ui.course.introduce.brief.BriefIntroductionFragment
import com.sandgrains.edu.student.ui.course.introduce.catalogue.CatalogueFragment
import com.sandgrains.edu.student.utils.base.BaseActivity
import com.sandgrains.edu.student.utils.ViewPager2Util
import com.sandgrains.edu.student.utils.custom.PlaceHolderLayout
import com.sandgrains.edu.student.utils.custom.video.TryWatchPopupView
import com.sandgrains.edu.student.utils.setStatusBarVisibility

class CourseIntroduceActivity : BaseActivity() {
    private val binding: ActivityCourseIntroduceBinding by viewbind()
    private val viewModel by lazy { ViewModelProvider(this).get(CourseIntroduceViewModel::class.java) }
    private lateinit var placeholderView: PlaceHolderLayout
    private var courseId: String? = null
    override fun getContentLayoutId(): Int {
        return R.layout.activity_course_introduce
    }

    override fun initWidget() {
        setStatusBarVisibility(window, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        val viewpager = binding.viewpager1
        val placeholderLayout = binding.placeholderView
        this.placeholderView = placeholderLayout
        placeholderLayout.bindView(
            arrayOf(
                binding.layBottom,
                binding.nestedLinearLayout,
                binding.layTab
            )
        )
        placeholderLayout.setOnRetryOnClickListener {
            val placeholderView = placeholderView
            val courseId = courseId
            if (courseId == null) {
                placeholderView.showErrorView("课程id为null")
            } else {
                placeholderView.showLoadView()
                viewModel.loadData(courseId)
            }
        }
        viewpager.adapter = PagerAdapter(this)
        viewpager.offscreenPageLimit = 2
        ViewPager2Delegate.install(viewpager, binding.tabLayout)
        ViewPager2Util.changeToNeverMode(viewpager)

        binding.ivTryWatch.setOnClickListener {
            course.tryWatchVideoInfoList?.let { list ->
                if (list.isEmpty()) {
                    Toast.makeText(this, "该课程没有试看视频", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                var popupView: BasePopupView? = null
                val tryWatchPopupView = TryWatchPopupView(this, ArrayList(list)) {
                    popupView?.dismiss()
                }

                popupView = XPopup.Builder(this)
                    .isDestroyOnDismiss(true)
                    .asCustom(tryWatchPopupView)
                    .show()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private lateinit var course: Course
    override fun initObserve() {
        viewModel.loadData.observe(this, { result ->
            val course = result.getOrNull()
            if (course != null) {
                this.course = course
                loadCourse(course)
            } else {
                val exception = result.exceptionOrNull()
                if (exception != null && exception !is Repository.CustomException) {
                    placeholderView.showNetErrorView()
                } else {
                    placeholderView.showErrorView(exception!!.message ?: "未知异常，请重试！")
                }
            }
        })
    }

    private fun loadCourse(course: Course) {
        binding.tvCourseName.text = course.name
        placeholderView.loaded()
    }

    override fun loadData() {
        val courseId = intent.getStringExtra("course_id") ?: "1"
        this.courseId = courseId
        placeholderView.apply {
            if (courseId == null) {
                showErrorView("课程id为null")
            } else {
                showLoadView()
                viewModel.loadData(courseId)
            }
        }

    }

    class PagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val fragments: SparseArray<Fragment> = SparseArray()

        init {
            fragments.put(1, CatalogueFragment())
            fragments.put(2, BriefIntroductionFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size()
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position + 1]
        }

    }
}