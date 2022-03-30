package com.sandgrains.edu.student.utils.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    protected val _requestException = MutableLiveData<NetRequestException>()
    val requestException:LiveData<NetRequestException> = _requestException
}