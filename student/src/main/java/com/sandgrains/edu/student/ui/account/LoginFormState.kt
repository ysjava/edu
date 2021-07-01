package com.sandgrains.edu.student.ui.account

data class LoginFormState(
        val phoneError: Int? = null,
        val pawError: Int? = null,
        val isDataValid: Boolean = false,
        val loginType: String = "CODE"
)