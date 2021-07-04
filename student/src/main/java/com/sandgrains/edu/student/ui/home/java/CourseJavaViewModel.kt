package com.sandgrains.edu.student.ui.home.java

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sandgrains.edu.student.logic.Repository

class CourseJavaViewModel : ViewModel() {
    private val _loadData = MutableLiveData<String>()
    private val _getCourseSortedList = MutableLiveData<String>()
    val loadData = Transformations.switchMap(_loadData) {
        Repository.getJavaCourseData()
    }
    val getCourseSortedList = Transformations.switchMap(_getCourseSortedList) { sort ->
        Repository.getCourseSortedList(sort)
    }

    fun loadData() {
        this._loadData.value = "_loadData.value"
    }

    //对课程进行条件排序查询
    // sort: 默认，最新，销量
    fun getCourseSortedList(sort: String) {
        this._getCourseSortedList.value = sort
    }

}