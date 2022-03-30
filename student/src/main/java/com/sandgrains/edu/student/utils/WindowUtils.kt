package com.sandgrains.edu.student.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.ColorRes

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

fun setStatusBarColor(window: Window,@ColorRes colorId: Int) {
    window.statusBarColor = window.context.resources.getColor(colorId,null)
}

/**
 * @param visibility
 * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //状态栏隐藏
 * View.SYSTEM_UI_FLAG_VISIBLE //状态栏显示
 * View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //状态栏字体颜色为黑色
 * View.SYSTEM_UI_FLAG_LAYOUT_STABLE //状态栏字体颜色跟随系统 好像都是白的
 * */
fun setStatusBarVisibility(window: Window,visibility: Int){
    window.decorView.systemUiVisibility = visibility
}