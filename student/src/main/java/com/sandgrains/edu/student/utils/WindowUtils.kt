package com.sandgrains.edu.student.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window

fun initWindow(window: Window) {
    val decorView = window.decorView
    decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    window.statusBarColor = Color.TRANSPARENT

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
        val window = window
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}