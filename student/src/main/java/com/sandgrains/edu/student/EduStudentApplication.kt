package com.sandgrains.edu.student

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.igexin.sdk.PushManager
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.download.BaseImageDownloader
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import tv.danmaku.ijk.media.player.IjkMediaPlayer

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
        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);
        initImageLoader()
    }

    private fun initImageLoader() {
        val config = ImageLoaderConfiguration.Builder(this).imageDownloader(
                BaseImageDownloader(this, 60 * 1000, 60 * 1000)) // connectTimeout超时时间
                .build()
        ImageLoader.getInstance().init(config)
    }
}