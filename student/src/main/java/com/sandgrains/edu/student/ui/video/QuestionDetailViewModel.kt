package com.sandgrains.edu.student.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sandgrains.edu.student.logic.Repository

class QuestionDetailViewModel: ViewModel() {
    private val _getQuestionById = MutableLiveData<String>()
    val getQuestionById = Transformations.switchMap(_getQuestionById) { id ->
        Repository.getQuestionById(id)
    }

    fun getQuestionById(id: String){
        this._getQuestionById.value = id

    }
}