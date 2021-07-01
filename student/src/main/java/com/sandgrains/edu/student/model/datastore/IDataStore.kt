package com.sandgrains.edu.student.model.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow


interface IDataStore {

    suspend fun putString(key: Preferences.Key<String>, value: String)
    fun getString(key: Preferences.Key<String>): Flow<String>

    suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean)
    fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean>

    suspend fun putInt(key: Preferences.Key<Int>, value: Int)
    fun getInt(key: Preferences.Key<Int>): Flow<Int>

    suspend fun putLong(key: Preferences.Key<Long>, value: Long)
    fun getLong(key: Preferences.Key<Long>): Flow<Long>

    suspend fun putFloat(key: Preferences.Key<Float>, value: Float)
    fun getFloat(key: Preferences.Key<Float>): Flow<Float>

    suspend fun putDouble(key: Preferences.Key<Double>, value: Double)
    fun getDouble(key: Preferences.Key<Double>): Flow<Double>

}