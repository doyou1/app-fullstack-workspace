package com.example.accountbookuisampling.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.adapter.RegisterViewPagerAdapter
import com.example.accountbookuisampling.databinding.ActivityRegisterBinding
import com.example.accountbookuisampling.util.CONSUMPTION
import com.example.accountbookuisampling.util.INCOME
import com.example.accountbookuisampling.util.TRANSFER
import com.google.android.material.tabs.TabLayoutMediator


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var adapter: RegisterViewPagerAdapter
    private lateinit var tlm: TabLayoutMediator
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액티비티 시작시 애니메이션
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

        setTabLayout()
        setClickEvent()
    }

    private fun setTabLayout() {
        adapter = RegisterViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        tlm = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = INCOME
                }
                1 -> {
                    tab.text = CONSUMPTION
                }
                2 -> {
                    binding.title = TRANSFER
                    tab.text = TRANSFER
                }
            }
        }
        tlm.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when (position) {
                    0 -> binding.title = INCOME
                    1 -> binding.title = CONSUMPTION
                    2 -> binding.title = TRANSFER
                }

            }
        })
    }

    private fun setClickEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onResume() {
        super.onResume()

        if (!tlm.isAttached) {
            tlm.attach()
        }
    }

    override fun onStop() {
        super.onStop()
        tlm.detach()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        // 액티비티 종료시 애니메이션
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}