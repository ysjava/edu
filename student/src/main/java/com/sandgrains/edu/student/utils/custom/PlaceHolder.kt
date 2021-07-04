package com.sandgrains.edu.student.utils.custom

/**
 * 占位符布局接口
 *
 * */
interface PlaceHolder {

    /**
     * 空布局
     * */
    fun showEmptyView()

    /**
     * 加载
     * */
    fun showLoadView()

    /**
     * 网络错误
     * */
    fun showNetErrorView()

    /**
     * 错误信息
     * */
    fun showErrorView(errorInfo: Int)
    fun showErrorView(errorInfo: String)

    /**
     * 加载完成
     * */
    fun loaded()
}