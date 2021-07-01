package com.sandgrains.edu.student.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentHomeBinding
import com.sandgrains.edu.student.utils.custom.PlaceHolderLayout

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewbind()

    //    private lateinit var placeHolder: PlaceHolderLayout
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val placeHolder = binding.placeholder
        binding.tvTest.setOnClickListener {
            placeHolder.showNetErrorView()
        }
        placeHolder.setOnRetryOnClickListener {
            placeHolder.showErrorView(R.string.invalid_paw)
        }
        placeHolder.setOnRefreshListener {
            var i = binding.tvTest.text.toString().toInt()
            i += 1
            binding.tvTest.text = i.toString()
            placeHolder.showLoadView()
            placeHolder.setRefreshLayoutRefreshing(false)
        }
    }
}