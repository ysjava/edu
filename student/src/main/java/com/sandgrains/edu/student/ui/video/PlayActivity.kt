package com.sandgrains.edu.student.ui.video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sandgrains.edu.student.R

/**
 * 视频播放界面
 *
 * */
class PlayActivity: AppCompatActivity(R.layout.activity_play) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val courseId = intent.getStringExtra("course_id")

    }
}