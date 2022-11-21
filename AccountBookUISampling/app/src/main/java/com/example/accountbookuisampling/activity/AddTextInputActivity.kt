package com.example.accountbookuisampling.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accountbookuisampling.databinding.ActivityAddTextInputBinding

class AddTextInputActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTextInputBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra("item", "추가1")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}