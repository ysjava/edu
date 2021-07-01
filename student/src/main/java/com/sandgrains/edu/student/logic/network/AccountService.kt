package com.sandgrains.edu.student.logic.network


import com.sandgrains.edu.student.model.ResultResponse
import com.sandgrains.edu.student.model.account.AccountRspModel
import com.sandgrains.edu.student.model.account.PawLoginModel
import retrofit2.Call
import retrofit2.http.Body
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


}