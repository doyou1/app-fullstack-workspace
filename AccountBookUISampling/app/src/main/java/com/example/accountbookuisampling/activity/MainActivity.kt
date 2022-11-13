package com.example.accountbookuisampling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.accountbookuisampling.adapter.MainViewPagerAdapter
import com.example.accountbookuisampling.databinding.ActivityMainBinding
import com.example.accountbookuisampling.util.*
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainViewPagerAdapter
    private lateinit var tlm: TabLayoutMediator


    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayout()
        setClickEvent()
    }

    private fun setTabLayout() {
        adapter = MainViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        tlm = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = DAY
                }
                1 -> {
                    tab.text = CALENDAR
                }
                2 -> {
                    tab.text = WEEK
                }
                3 -> {
                    tab.text = MONTH
                }
                4 -> {
                    tab.text = YEAR
                }
            }
        }

        tlm.attach()
    }

    override fun onResume() {
        super.onResume()

        if (!tlm.isAttached) {
            tlm.attach()
        }
    }

    private fun setClickEvent() {
        binding.btnAddHistory.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        tlm.detach()
    }

}