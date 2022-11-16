package com.example.custominputlayoutsampling

import android.content.Context
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEditTextEvent()
        binding.et1.showSoftInputOnFocus = false
        binding.et2.showSoftInputOnFocus = false
    }

    private fun setEditTextEvent() {
        binding.et1.setOnFocusChangeListener { view, hasFocus ->
            binding.isShow = hasFocus
        }

    }
}