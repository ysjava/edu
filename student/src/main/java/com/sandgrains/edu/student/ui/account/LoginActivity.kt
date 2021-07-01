package com.sandgrains.edu.student.ui.account

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sandgrains.edu.student.R

class LoginActivity : AppCompatActivity() {

    private lateinit var pawLoginFragment: Fragment
    private lateinit var codeLoginFragment: Fragment
    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onObserve()
        codeLoginFragment = CodeLoginFragment()
        pawLoginFragment = PawLoginFragment()

        addFragment()
    }

    private fun onObserve() {
        viewModel.skipLoginView.observe(this, {
            skipFragment(it)
        })
    }

    private fun addFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.lay_container, codeLoginFragment)
                .add(R.id.lay_container, pawLoginFragment)
                .show(codeLoginFragment)
                .hide(pawLoginFragment)
                .commit()
    }

    private fun skipFragment(type: String) {
        val pair = if (type == "CODE") Pair(codeLoginFragment, pawLoginFragment) else Pair(pawLoginFragment, codeLoginFragment)
        supportFragmentManager
                .beginTransaction()
                .show(pair.first)
                .hide(pair.second)
                .commit()
    }
}