package com.sandgrains.edu.student.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.ActivityRetrievePawBinding

class RetrievePawActivity : AppCompatActivity(R.layout.activity_retrieve_paw) {
    private val binding: ActivityRetrievePawBinding by viewbind()
    private val viewModel by lazy { ViewModelProvider(this).get(RetrievePawViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val back = binding.ivBack
        val phoneEditText = binding.etPhone
        val next = binding.btnNext

        viewModel.requestCode.observe(this, { result ->
            //if (result.isFailure) return@observe
            //请求成功后开始跳转等待短信
            startActivity(Intent(this@RetrievePawActivity, InputCodeActivity::class.java))
        })

        back.setOnClickListener {
            finish()
        }

        next.setOnClickListener {
//            viewModel.requestCode()
            startActivity(Intent(this@RetrievePawActivity, InputCodeActivity::class.java))
        }

        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //ignore
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                next.isEnabled = phoneEditText.text.toString().length == 11
            }

            override fun afterTextChanged(s: Editable?) {
                //ignore
            }
        }

        phoneEditText.addTextChangedListener(textChangeListener)
    }


}