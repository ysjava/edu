package com.sandgrains.edu.student.logic.network


import com.sandgrains.edu.student.model.account.PawLoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object EduNetwork {

    private val accountService = ServiceCreator.create(AccountService::class.java)

    //密码登陆
    suspend fun loginByPaw(model: PawLoginModel) = accountService.loginByPaw(model).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun requestCode(phone: String) = accountService.requestCode(phone).await()
    suspend fun getJavaCourseData() = accountService.getJavaCourseData().await()
    suspend fun getCourseSortedList(sort: String) = accountService.getCourseSortedList(sort).await()
    suspend fun getCourseById(courseId: String) = accountService.getCourseById(courseId).await()
    suspend fun getQuestionsBySectionId(sectionId: String) = accountService.getQuestionsBySectionId(sectionId).await()
    suspend fun getTotalNumberOfQuestionsByCourseId(courseId: String) = accountService.getTotalNumberOfQuestionsByCourseId(courseId).await()
    suspend fun getQuestionById(id: String) = accountService.getQuestionById(id).await()

}