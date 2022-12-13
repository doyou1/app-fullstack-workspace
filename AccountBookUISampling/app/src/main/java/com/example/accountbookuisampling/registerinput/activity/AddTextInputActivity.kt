package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.util.*

class AddTextInputActivity: AppCompatActivity() {

    private lateinit var binding: ViewDataBinding
    private val TAG = this::class.java.simpleName
    private var currentView = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(intent.getIntExtra(TEXT_FLAG, -1)) {
            FLAG_ASSET -> {
                binding = ActivityAddAssetTextInputBinding.inflate(layoutInflater)
                setAssetClickEvent()
            }
            FLAG_CATEGORY -> {
                binding = ActivityAddCategoryTextInputBinding.inflate(layoutInflater)
                setCategoryClickEvent()
            }
            else -> {
                throw NotImplementedError()
            }
        }
        currentView = intent.getIntExtra(TEXT_CURRENT_VIEW, -1)
        setContentView(binding.root)
    }

    private fun setAssetClickEvent() {
        val _binding = binding as ActivityAddAssetTextInputBinding
        _binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TEXT_GROUP_ID, 0)
            intent.putExtra(TEXT_NAME, "NAME")
            intent.putExtra(TEXT_AMOUNT, 100)
            intent.putExtra(TEXT_MEMO, "MEMO")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setCategoryClickEvent() {
        val _binding = binding as ActivityAddCategoryTextInputBinding
        _binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TEXT_NAME, "NAME")
            intent.putExtra(TEXT_CURRENT_VIEW, currentView)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}