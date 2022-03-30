package com.sandgrains.edu.student.utils.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.setPadding(view.paddingLeft, getStatusHeight() + view.paddingTop, view.paddingRight, view.bottom)
//    }
//
//    open fun getStatusHeight():Int{
//        return com.sandgrains.edu.student.utils.getStatusHeight()
//    }
}