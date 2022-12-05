package com.example.accountbookuisampling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.accountbookuisampling.adapter.viewpager.MainViewPagerAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.ActivityMainBinding
import com.example.accountbookuisampling.fragment.main.*
import com.example.accountbookuisampling.util.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tlm: TabLayoutMediator
    private lateinit var _fa: FragmentActivity

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _fa = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setTopDateText()
        setViewPager()
        setTabLayout()
        setClickEvent()
    }

    private fun setTopDateText() {
        SharedPreferenceUtil.getDate()?.let {
            if (it.isNotEmpty()) {
                val yyyy = it.substring(0, 4)
                val MM = it.substring(4, 6)
                val dd = it.substring(6, 8)
                binding.tvDate.text = "${yyyy}년 ${MM}월 ${dd}일"
            }
        }
    }

    private fun setViewPager() {
        binding.viewPager.adapter = MainViewPagerAdapter(this)
        binding.viewPager.setCurrentItem(1, false)
    }

    private fun setTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                SharedPreferenceUtil.setCurrentItem(ConvertUtil.getIdByTitle(tab?.text.toString()))
                binding.viewPager.adapter = MainViewPagerAdapter(_fa)
                binding.viewPager.setCurrentItem(1, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setClickEvent() {
        binding.btnAddHistory.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLeft.setOnClickListener {
//            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }

        binding.btnRight.setOnClickListener {
//            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
    }
}