package com.sandgrains.edu.student

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.igexin.sdk.PushManager

class EduStudentApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //个推初始化
        PushManager.getInstance().initialize(applicationContext)

        PushManager.getInstance().setDebugLogger(this) { s ->
            Log.i("PUSH_LOG", s)
        }
    }
}