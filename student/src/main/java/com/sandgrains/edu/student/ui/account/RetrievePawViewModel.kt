package com.sandgrains.edu.student.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sandgrains.edu.student.logic.Repository

class RetrievePawViewModel : ViewModel() {

    private val _requestCode = MutableLiveData<String>()
    val requestCode = Transformations.switchMap(_requestCode){ phone ->
        Repository.requestCode(phone)
    }

    fun requestCode() {
        this._requestCode.value = "_requestCode.value"
    }
}