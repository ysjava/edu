package com.sandgrains.edu.student.ui.home.java

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sandgrains.edu.student.logic.Repository
import com.sandgrains.edu.student.model.Course
import com.sandgrains.edu.student.ui.home.JavaRecommend
import com.sandgrains.edu.student.utils.base.BaseViewModel
import com.sandgrains.edu.student.utils.base.NetRequestException
import com.sandgrains.edu.student.utils.base.NetWorkError
import com.sandgrains.edu.student.utils.base.ServiceError
import com.sandgrains.edu.student.utils.logd
import com.sandgrains.edu.student.utils.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import java.text.FieldPosition

class CourseJavaViewModel : BaseViewModel() {
    private val _loadData = MutableLiveData<String>()
    private val _loadData2 = MutableLiveData<String>()
    private val _getCourseSortedList = MutableLiveData<String>()
    private val _checkCourseIsPay = MutableLiveData<String>()


    val loadData = Transformations.switchMap(_loadData) {
        Repository.getJavaCourseData()
    }

    val loadData2 = Transformations.switchMap(_loadData2) {
        Repository.getJavaCourseData2()
    }

    val checkCourseIsPay = Transformations.switchMap(_checkCourseIsPay) {
        Repository.checkCourseIsPay(it)
    }

    val getCourseSortedList = Transformations.switchMap(_getCourseSortedList) { sort ->
        Repository.getCourseSortedList(sort)
    }

    fun loadData() {
        this._loadData.value = "_loadData.value"
    }

    fun loadData2() {
        this._loadData2.value = "_loadData.value"
    }

    //对课程进行条件排序查询
    // sort: 默认，最新，销量
    fun getCourseSortedList(sort: String) {
        this._getCourseSortedList.value = sort
    }

    fun checkCourseIsPay(id: String) {
        this._checkCourseIsPay.value = id
    }

//    fun getCoursesByTypeAndSort(type: Int = 1, sort: String): Flow<PagingData<Course>> {
//        return Repository.getCoursesByTypeAndSort(1, sort)
//                .cachedIn(viewModelScope)
//    }

    fun getCoursesByCustom(): Flow<PagingData<JavaRecommend>> {
        return Repository.getCoursesByCustom(catchError)
                .cachedIn(viewModelScope)
    }

    private val catchError = { e: NetRequestException ->
        _requestException.value = e
    }
}