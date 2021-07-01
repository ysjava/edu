package com.sandgrains.edu.student.persistence

import android.content.Context
import android.text.TextUtils
import com.sandgrains.edu.student.EduStudentApplication
import com.sandgrains.edu.student.model.account.AccountRspModel
import com.sandgrains.edu.student.model.datastore.DataStoreRepository
import com.sandgrains.edu.student.model.datastore.PreferencesKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


// 设备的推送Id
private var pushId: String = ""

// 设备Id是否已经绑定到了服务器
private var isBind = false

// 登录状态的Token，用来接口请求
private var token: String = ""

// 登录的用户ID
private var accountId: String = ""

// 登录的账户
private var account: String = ""

/**
 * 存储数据到XML文件，持久化
 */
private fun save(context: Context) {
    val dataStore = DataStoreRepository(context)
    runBlocking(Dispatchers.IO) {
        dataStore.apply {
            putString(PreferencesKeys.KEY_PUSH_ID, pushId)
//            putBoolean(PreferencesKeys.KEY_IS_BIND, isBind)
            putString(PreferencesKeys.KEY_TOKEN, token)
            putString(PreferencesKeys.KEY_USER_ID, accountId)
            putString(PreferencesKeys.KEY_ACCOUNT, account)
        }
    }
}

/**
 * 进行数据加载
 */
fun load(context: Context) {
    val dataStore = DataStoreRepository(context)
    runBlocking(Dispatchers.IO) {
        dataStore.apply {
            pushId = getStringOnly(PreferencesKeys.KEY_PUSH_ID)
//            isBind = getBooleanOnly(PreferencesKeys.KEY_IS_BIND)
            token = getStringOnly(PreferencesKeys.KEY_TOKEN)
            accountId = getStringOnly(PreferencesKeys.KEY_USER_ID)
            account = getStringOnly(PreferencesKeys.KEY_ACCOUNT)

        }
    }
}

/**
 * 返回当前账户是否登录
 *
 * @return True已登录
 */
fun isLogin(): Boolean {
    // 账户Id 和 Token 不为空
    return (!TextUtils.isEmpty(accountId)
            && !TextUtils.isEmpty(token))
}

fun login(model: AccountRspModel) {
    token = model.token
    account = model.card.phone
    accountId = model.card.id

    save(EduStudentApplication.context)

}

fun setPushId(id: String) {
    pushId = id
}

fun getPushId(): String {
    return pushId
}

fun isBind(): Boolean {
    return isBind
}

class Account {

}