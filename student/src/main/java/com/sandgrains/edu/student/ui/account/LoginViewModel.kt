package com.sandgrains.edu.student.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.logic.Repository
import com.sandgrains.edu.student.model.account.PawLoginModel
import java.util.regex.Pattern

const val REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}\$"

class LoginViewModel : ViewModel() {
    private val _loginFormState = MutableLiveData<LoginFormState>()
    private val _loginByPaw = MutableLiveData<PawLoginModel>()
    private val _requestCode = MutableLiveData<String>()
    val skipLoginView = MutableLiveData<String>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState
    val requestCode = Transformations.switchMap(_requestCode) { phone ->
        Repository.requestCode(phone)
    }
    val loginByPaw = Transformations.switchMap(_loginByPaw) { model ->
        Repository.loginByPaw(model)
    }

    /**
     * 切换登陆方式
     *
     * @param string 切换到哪一种登陆方式  验证码登陆："CODE"  2密码登陆："PAW"
     * */
    fun skipLoginView(string: String) {
        this.skipLoginView.value = string
    }

    /**
     * 验证码登陆数据改变
     *
     * */
    fun loginDataChangeByCode(phone: String) {
        if (!isUserPhoneValid(phone)) {
            _loginFormState.value = LoginFormState(phoneError = R.string.invalid_phone)
        } else {
            _loginFormState.value = LoginFormState(isDataValid = true)
        }
    }

    /**
     * 账号密码登陆数据改变
     *
     * */
    fun loginDataChangeByPaw(phone: String, paw: String) {
        if (!isUserPhoneValid(phone)) {
            _loginFormState.value = LoginFormState(phoneError = R.string.invalid_phone, loginType = "PAW")
        } else if (!isPasswordValid(paw)) {
            _loginFormState.value = LoginFormState(pawError = R.string.invalid_paw, loginType = "PAW")
        } else {
            _loginFormState.value = LoginFormState(isDataValid = true, loginType = "PAW")
        }
    }

    /**
     * 密码规范验证
     *
     * */
    private fun isPasswordValid(paw: String): Boolean {
        return paw.length >= 6
    }

    /**
     * 手机号码正则验证
     *
     * */
    private fun isUserPhoneValid(phone: String): Boolean {
        return Pattern.matches(REGEX_MOBILE, phone)
    }

    fun requestCode() {
        this._requestCode.value = "_requestCode.value"
    }

    fun loginByPaw(phone: String, paw: String) {
        this._loginByPaw.value = PawLoginModel(phone, paw)
    }
}