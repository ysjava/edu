package com.sandgrains.edu.student.model.account

import com.sandgrains.edu.student.card.AccountCard


data class PawLoginModel(val phone: String, val password: String)
data class CodeLoginModel(val phone: String, val code: String)

data class AccountRspModel(
        val card: AccountCard,
        val token: String,
        val isBind: Boolean
)