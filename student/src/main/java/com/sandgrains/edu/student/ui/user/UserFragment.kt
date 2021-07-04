package com.sandgrains.edu.student.ui.user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentCourseJavaBinding
import com.sandgrains.edu.student.databinding.FragmentUserBinding

class UserFragment: Fragment(R.layout.fragment_user) {
    private val binding: FragmentUserBinding by viewbind()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}