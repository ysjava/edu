package com.sandgrains.edu.student.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.ui.MainActivity
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentPawLoginBinding
import com.sandgrains.edu.student.persistence.login

class PawLoginFragment : Fragment(R.layout.fragment_paw_login) {
    private val binding: FragmentPawLoginBinding by viewbind()
    private val viewModel by lazy { ViewModelProvider(activity!!).get(LoginViewModel::class.java) }
    private lateinit var skipCodeLogin: TextView
    private lateinit var forgetPaw: TextView
    private lateinit var inValidPhoneText: TextView
    private lateinit var inValidPawText: TextView
    private lateinit var phoneEditText: EditText
    private lateinit var pawEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var cleanTextImg: ImageView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        phoneEditText = binding.etPhone
        cleanTextImg = binding.ivCleanText
        skipCodeLogin = binding.tvLoginCode
        forgetPaw = binding.tvForgetPaw
        pawEditText = binding.etPaw
        loginButton = binding.btnLogin
        inValidPhoneText = binding.tvInvalidPhone
        inValidPawText = binding.tvInvalidPaw

        onClick()

        viewModel.loginFormState.observe(viewLifecycleOwner, { loginFormState ->
            if (loginFormState == null || loginFormState.loginType != "PAW") return@observe
            cleanTextImg.visibility = if (phoneEditText.text.toString().isNotEmpty()) View.VISIBLE else View.GONE
            loginButton.isEnabled = loginFormState.isDataValid
            if (loginFormState.phoneError != null) inValidPhoneText.setText(loginFormState.phoneError) else inValidPhoneText.text = ""
            if (loginFormState.pawError != null) inValidPawText.setText(loginFormState.pawError) else inValidPawText.text = ""

        })

        viewModel.loginByPaw.observe(viewLifecycleOwner, { result ->
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
//            val data = result.getOrNull()
//
//            if (data != null) {
//                //部分账户数据保存到本地
//                login(data)
//                //跳转主页面
//                startActivity(Intent(activity, MainActivity::class.java))
//            } else {
//                setViewEnable(true)
//                loginButton.text = "登陆"
//                Toast.makeText(activity, "登陆失败：${result.exceptionOrNull()}", Toast.LENGTH_SHORT).show()
//            }
        })

        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //ignore
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //每次输入框改变就进行验证
                viewModel.loginDataChangeByPaw(phoneEditText.text.toString(), pawEditText.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                //ignore
            }
        }

        binding.etPhone.addTextChangedListener(textChangeListener)
        binding.etPaw.addTextChangedListener(textChangeListener)
    }

    private fun onClick() {
        skipCodeLogin.setOnClickListener {
            viewModel.skipLoginView("CODE")
        }

        forgetPaw.setOnClickListener {
            Toast.makeText(activity, "太穷，用不了验证码！！", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(activity, RetrievePawActivity::class.java))
        }

        cleanTextImg.setOnClickListener {
            phoneEditText.setText("")
        }

        loginButton.setOnClickListener {
            setViewEnable(false)
            loginButton.text = "登陆中..."
            viewModel.loginByPaw(phoneEditText.text.toString(), pawEditText.text.toString())
        }
    }

    private fun setViewEnable(isEnable: Boolean) {
        pawEditText.isEnabled = isEnable
        loginButton.isEnabled = isEnable
        cleanTextImg.isEnabled = isEnable
        phoneEditText.isEnabled = isEnable
    }

}