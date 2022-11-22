package com.example.accountbookuisampling.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.util.FLAG_ASSET
import com.example.accountbookuisampling.util.FLAG_CATEGORY

class AddTextInputActivity: AppCompatActivity() {

    private lateinit var binding: ViewDataBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flag = intent.getIntExtra("flag", -1)
        when(flag) {
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

        setContentView(binding.root)

    }

    private fun setAssetClickEvent() {
        val _binding = binding as ActivityAddAssetTextInputBinding
        _binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra("item", "추가1")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setCategoryClickEvent() {
        val _binding = binding as ActivityAddCategoryTextInputBinding
        _binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra("item", "추가1")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}