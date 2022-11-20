package com.example.accountbookuisampling.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accountbookuisampling.databinding.ActivityAddTextInputBinding

class AddTextInputActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTextInputBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = ActivityAddTextInputBinding.inflate(layoutInflater)

    }
}