package com.sandgrains.edu.student.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.FragmentCodeLoginBinding

class CodeLoginFragment : Fragment(R.layout.fragment_code_login) {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(LoginViewModel::class.java) }

    private val binding: FragmentCodeLoginBinding by viewbind()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val phoneEditText = binding.etPhone
        val loginButton = binding.btnGetCode
        val inValidText = binding.tvInvalidPhone
        val cleanTextImg = binding.ivCleanText
        val skipPawLogin = binding.tvLoginPaw
        cleanTextImg.setOnClickListener {
            phoneEditText.setText("")
        }

        loginButton.setOnClickListener {
            Toast.makeText(activity, "太穷，暂不开通短信服务，请使用密码登陆！", Toast.LENGTH_SHORT).show()
//            viewModel.requestCode()
        }

        skipPawLogin.setOnClickListener {
            viewModel.skipLoginView("PAW")
        }

        viewModel.loginFormState.observe(viewLifecycleOwner, { loginFormState ->
            if (loginFormState == null || loginFormState.loginType != "CODE") return@observe
            cleanTextImg.visibility = if (phoneEditText.text.toString().isNotEmpty()) View.VISIBLE else View.GONE
            loginButton.isEnabled = loginFormState.isDataValid
            if (loginFormState.phoneError != null) {
                inValidText.visibility = View.VISIBLE
                inValidText.setText(loginFormState.phoneError)
            } else {
                inValidText.visibility = View.GONE
            }
        })

        viewModel.requestCode.observe(viewLifecycleOwner, { result ->
            if (result.isFailure) return@observe
            //请求成功后跳转等待短信
            startActivity(Intent(activity, InputCodeActivity::class.java))
        })

        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //ignore
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //每次输入框改变就进行验证
                viewModel.loginDataChangeByCode(phoneEditText.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                //ignore
            }
        }

        binding.etPhone.addTextChangedListener(textChangeListener)
    }

}