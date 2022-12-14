package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.util.*

class AddCategoryInputActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryTextInputBinding
    private val TAG = this::class.java.simpleName
    private var currentView = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryTextInputBinding.inflate(layoutInflater)
        currentView = intent.getIntExtra(TEXT_CURRENT_VIEW, -1)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TEXT_NAME, "NAME")
            intent.putExtra(TEXT_CURRENT_VIEW, currentView)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}