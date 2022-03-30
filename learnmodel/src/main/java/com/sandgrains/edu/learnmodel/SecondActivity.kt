package com.sandgrains.edu.learnmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
private var instance: SecondActivity? = null
class SecondActivity:AppCompatActivity(R.layout.activity_second) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this
    }
}