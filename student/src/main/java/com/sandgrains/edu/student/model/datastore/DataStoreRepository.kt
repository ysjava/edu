package com.sandgrains.edu.student.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.sandgrains.edu.student.model.datastore.IDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class DataStoreRepository(context: Context) : IDataStore {

    private val PREFERENCE_NAME = "DataStore"
    private var dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    override suspend fun putString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { it[key] = value }
    }

    override fun getString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { it[key] ?: "" }
    }

    suspend fun getStringOnly(key: Preferences.Key<String>): String {
        var str = ""
        getString(key).collect { str = it }
        return str
    }

    override suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { it[key] = value }
    }

    override fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { it[key] ?: false }
    }

    suspend fun getBooleanOnly(key: Preferences.Key<Boolean>): Boolean {
        var b = false
        getBoolean(key).collect { b = it }
        return b
    }

    override suspend fun putInt(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit { it[key] = value }
    }

    override fun getInt(key: Preferences.Key<Int>): Flow<Int> {
        return dataStore.data.map { it[key] ?: 0 }
    }

    override suspend fun putLong(key: Preferences.Key<Long>, value: Long) {
        dataStore.edit { it[key] = value }
    }

    override fun getLong(key: Preferences.Key<Long>): Flow<Long> {
        return dataStore.data.map { it[key] ?: 0L }
    }

    override suspend fun putFloat(key: Preferences.Key<Float>, value: Float) {
        dataStore.edit { it[key] = value }
    }

    override fun getFloat(key: Preferences.Key<Float>): Flow<Float> {
        return dataStore.data.map { it[key] ?: 0F }
    }

    override suspend fun putDouble(key: Preferences.Key<Double>, value: Double) {
        dataStore.edit { it[key] = value }
    }

    override fun getDouble(key: Preferences.Key<Double>): Flow<Double> {
        return dataStore.data.map { it[key] ?: 0.0 }
    }


}