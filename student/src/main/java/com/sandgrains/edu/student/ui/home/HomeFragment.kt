package com.sandgrains.edu.student.ui.home

import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentHomeBinding
import com.sandgrains.edu.student.ui.home.java.CourseJavaFragment
import com.sandgrains.edu.student.ui.home.python.CoursePythonFragment
import com.sandgrains.edu.student.ui.home.web.CourseWebFragment
import com.sandgrains.edu.student.utils.ViewPager2Util

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewbind()

    //    private lateinit var placeHolder: PlaceHolderLayout
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewpager = binding.viewpager
        viewpager.adapter = PagerAdapter(activity!!)
        viewpager.offscreenPageLimit = 2
        ViewPager2Delegate.install(viewpager, binding.tabLayout)
        ViewPager2Util.changeToNeverMode(viewpager)

    }


    class PagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val fragments: SparseArray<Fragment> = SparseArray()

        init {
            fragments.put(1, CourseJavaFragment())
            fragments.put(2, CourseWebFragment())
            fragments.put(3, CoursePythonFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size()
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position + 1]
        }

    }
}