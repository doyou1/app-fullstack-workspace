package com.example.accountbookuisampling.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.accountbookuisampling.fragment.main.*
import com.example.accountbookuisampling.util.MAIN_FRAGMENT_COUNT

class MainViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return MAIN_FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                DayFragment.getInstance()
            }
            1 -> {
                CalendarFragment.getInstance()
            }
            2 -> {
                WeekFragment.getInstance()
            }
            3 -> {
                MonthFragment.getInstance()
            }
            4 -> {
                YearFragment.getInstance()
            }
            else -> {
                DayFragment.getInstance()
            }
        }
    }
}