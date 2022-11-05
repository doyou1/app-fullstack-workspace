package com.example.accountbookuisampling.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.accountbookuisampling.fragment.*
import com.example.accountbookuisampling.util.FRAGMENT_COUNT

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                DayFragment.newInstance()
            }
            1 -> {
                CalendarFragment.newInstance()
            }
            2 -> {
                WeekFragment.newInstance()
            }
            3 -> {
                MonthFragment.newInstance()
            }
            4 -> {
                YearFragment.newInstance()
            }
            else -> {
                DayFragment.newInstance()
            }
        }
    }
}