package com.sandgrains.edu.student.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.PopupTryWatchBinding
import com.sandgrains.edu.student.utils.logd

class TestActivity:AppCompatActivity(R.layout.popup_try_watch) {
    val binding: PopupTryWatchBinding by viewbind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tryWatchViewGroup.configTryWatchConfig {
            onSelectViewChange = { fromView, targetView, index ->
                "index $index".logd()
            }
        }
    }
}