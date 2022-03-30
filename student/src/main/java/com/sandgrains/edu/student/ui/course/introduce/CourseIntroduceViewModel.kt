package com.sandgrains.edu.student.ui.course.introduce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sandgrains.edu.student.logic.Repository

class CourseIntroduceViewModel : ViewModel() {
    private val _loadData = MutableLiveData<String>()
    val loadData = Transformations.switchMap(_loadData) {
        Repository.getCourseById(it)
    }

    fun loadData(courseId: String) {
        this._loadData.value = courseId
    }
}