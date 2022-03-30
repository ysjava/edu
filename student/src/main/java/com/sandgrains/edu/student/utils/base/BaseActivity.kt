package com.sandgrains.edu.student.utils.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayoutId())
        initWidget()
        initObserve()
        loadData()
    }



    abstract fun getContentLayoutId():Int

    protected open fun initWidget(){}
    protected open fun initObserve(){}
    protected open fun loadData(){}
}