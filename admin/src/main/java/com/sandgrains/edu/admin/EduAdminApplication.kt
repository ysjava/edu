package com.sandgrains.edu.admin

import android.app.Application
import android.util.Log
import com.igexin.sdk.PushManager

class EduAdminApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //个推初始化
        PushManager.getInstance().initialize(applicationContext)

        PushManager.getInstance().setDebugLogger(this) { s ->
            Log.i("PUSH_LOG", s)
        }
    }
}