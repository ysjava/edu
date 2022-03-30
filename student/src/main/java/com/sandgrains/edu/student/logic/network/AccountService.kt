package com.sandgrains.edu.student.logic.network


import com.sandgrains.edu.student.model.*
import com.sandgrains.edu.student.model.account.AccountRspModel
import com.sandgrains.edu.student.model.account.PawLoginModel
import com.sandgrains.edu.student.ui.find.DataModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {

    /**
     * 密码登陆或者注册
     * 当账户没有注册时，先注册然后登陆，登陆进入后可以更改详细信息
     * @param model 登陆或注册信息
     * @return 返回的是ResultResponse<AccountRspModel>
     */
    @POST("student/account/login")
    fun loginByPaw(@Body model: PawLoginModel): Call<ResultResponse<AccountRspModel>>

    /**
     * 获取验证码
     *
     * @param phone 手机号码
     * @return 验证码
     */
    @POST("student/account/request")
    fun requestCode(phone: String): Call<ResultResponse<String>>



    @GET("student/getCourseSortedList")
    fun getCourseSortedList(sort: String): Call<ResultResponse<List<Course>>>

    @GET("student/course")
    fun getCourseById(courseId: String): Call<ResultResponse<Course>>

    @GET("student/getQuestionsBySectionId")
    fun getQuestionsBySectionId(sectionId: String): Call<ResultResponse<List<Question>>>

    @GET("student/getTotalNumberOfQuestionsByCourseId")
    fun getTotalNumberOfQuestionsByCourseId(courseId: String): Call<ResultResponse<String>>

    @GET("student/getQuestionById")
    fun getQuestionById(id: String): Call<ResultResponse<Question>>

    @GET("student/getQuestionById")
    fun checkCourseIsPay(id: String): Call<ResultResponse<Pair<String, String>>>

    @GET("student/getQuestionById")
    suspend fun getFindData(): ResultResponse<List<DataModel>>

    @GET("student/getQuestionById")
    suspend fun getCourses(page: Int, pageSize: Int): ResultResponse<List<Course>>

    @GET("student/getQuestionById")
    suspend fun getBannerData(): Call<ResultResponse<BannerModel>>
    @GET("student/getQuestionById")
    suspend fun getTypeRecomData(): Call<ResultResponse<List<TypeRecommendCourse>>>

}