package com.sandgrains.edu.student.ui.find

import android.os.Handler
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.sandgrains.edu.student.logic.Repository
import com.sandgrains.edu.student.utils.base.BaseViewModel
import com.sandgrains.edu.student.utils.base.NetWorkError
import com.sandgrains.edu.student.utils.base.ServiceError
import kotlinx.coroutines.flow.*

class FindViewModel : BaseViewModel() {
    private var _loadData = MutableLiveData<String>()

    val loadData = Transformations.switchMap(_loadData) {
        Repository.getFindData()
    }
//    val requestException = MutableLiveData<MyException>()
//    fun loadData() {
//        this._loadData.value = "this._loadData.value"
//    }

    fun loadData() = liveData {
        Repository.getFindData2()
                .catch {

                    _requestException.value = NetWorkError()
                }
                .collectLatest { result ->
                    emit(result)
//                    result.onSuccess {
//                        emit(result)
//                    }
//
//                    result.onFailure {
//                        _requestException.value = result.exceptionOrNull() as ServiceError
//                    }
                }
    }

}
