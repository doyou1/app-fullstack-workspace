package com.example.accountbookuisampling.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}