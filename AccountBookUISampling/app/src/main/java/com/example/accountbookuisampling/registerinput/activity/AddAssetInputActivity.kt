package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.util.*

class AddAssetInputActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetTextInputBinding
    private val TAG = this::class.java.simpleName
//    private var currentView = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
//        currentView = intent.getIntExtra(TEXT_CURRENT_VIEW, -1)
        setClickEVent()
    }

    private fun setClickEVent() {
        binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TEXT_GROUP_ID, 0)
            intent.putExtra(TEXT_NAME, "NAME")
            intent.putExtra(TEXT_AMOUNT, 100)
            intent.putExtra(TEXT_MEMO, "MEMO")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}