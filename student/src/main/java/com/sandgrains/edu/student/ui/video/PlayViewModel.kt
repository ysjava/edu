package com.sandgrains.edu.student.ui.video

import androidx.lifecycle.*
import com.sandgrains.edu.student.logic.Repository
import com.sandgrains.edu.student.model.Section

class PlayViewModel : ViewModel() {
    private val _loadData = MutableLiveData<String>()
    private val _sectionSelected = MutableLiveData<Pair<Section, String>>()
    private val _getQuestionsBySectionId = MutableLiveData<String>()
    private val _getTotalNumberOfQuestionsByCourseId = MutableLiveData<String>()
    val loadData = Transformations.switchMap(_loadData) { courseId ->
        Repository.getCourseById(courseId)
    }
    val getQuestionsBySectionId = Transformations.switchMap(_getQuestionsBySectionId) { sectionId ->
        Repository.getQuestionsBySectionId(sectionId)
    }
    val getTotalNumberOfQuestionsByCourseId = Transformations.switchMap(_getTotalNumberOfQuestionsByCourseId) { sectionId ->
        Repository.getTotalNumberOfQuestionsByCourseId(sectionId)
    }
    val sectionSelected: LiveData<Pair<Section, String>> = _sectionSelected
    fun loadData(courseId: String) {
        this._loadData.value = courseId
    }

    fun updateSectionSelected(pair: Pair<Section, String>) {
        this._sectionSelected.value = pair
    }

    fun getQuestionsBySectionId(sectionId: String) {
        this._getQuestionsBySectionId.value = sectionId
    }

    fun getTotalNumberOfQuestionsByCourseId(courseId: String) {
        this._getTotalNumberOfQuestionsByCourseId.value = courseId
    }

}