package com.sandgrains.edu.student.logic

import androidx.lifecycle.liveData
import com.sandgrains.edu.student.logic.network.EduNetwork
import com.sandgrains.edu.student.model.account.PawLoginModel

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
            liveData(context) {
                val result = try {
                    block()
                } catch (e: Exception) {
                    Result.failure(e)
                }
                emit(result)
            }
    /**
     * 登陆或注册
     *
     * @param model 登陆model
     *
     * */
    fun loginByPaw(model: PawLoginModel) = fire(Dispatchers.IO) {
        val response = EduNetwork.loginByPaw(model)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun requestCode(phone: String) = fire(Dispatchers.IO) {
        val response = EduNetwork.requestCode(phone)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun getJavaCourseData() = fire(Dispatchers.IO){
        val response = EduNetwork.getJavaCourseData()

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

    fun getCourseSortedList(sort: String) = fire(Dispatchers.IO){
        val response = EduNetwork.getCourseSortedList(sort)

        if (response.status == "ok") {
            val rspModel = response.data
            Result.success(rspModel)
        } else {
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }

}