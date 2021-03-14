package com.example.project2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class TransitActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

        setContentView(R.layout.activity_transit)
    }
}