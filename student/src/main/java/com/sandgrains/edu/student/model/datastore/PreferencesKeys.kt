package com.sandgrains.edu.student.model.datastore

import androidx.datastore.preferences.core.preferencesKey

object PreferencesKeys {
    val KEY_PUSH_ID = preferencesKey<String>("KEY_PUSH_ID")
    val KEY_IS_BIND = preferencesKey<Boolean>("KEY_IS_BIND")
    val KEY_TOKEN = preferencesKey<String>("KEY_TOKEN")
    val KEY_USER_ID = preferencesKey<String>("KEY_USER_ID")
    val KEY_ACCOUNT = preferencesKey<String>("KEY_ACCOUNT")
}