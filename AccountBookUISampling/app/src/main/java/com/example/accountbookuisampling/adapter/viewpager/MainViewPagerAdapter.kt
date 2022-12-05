package com.example.accountbookuisampling.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.accountbookuisampling.fragment.main.*
import com.example.accountbookuisampling.util.MAIN_FRAGMENT_COUNT
import com.example.accountbookuisampling.util.SharedPreferenceUtil

class MainViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        if(position == 0) {
            SharedPreferenceUtil.getDate()?.let {
                if(it.isNotEmpty()) {
                    val yyyy = it.substring(0, 4)
                    val MM = it.substring(4, 6)
                    val dd = it.substring(6, 8)
                    SharedPreferenceUtil.setDate("$yyyy${MM.toInt()-1}$dd")
                }
            }
            return createFragment(1)
        } else if(position == 2) {
            SharedPreferenceUtil.getDate()?.let {
                if(it.isNotEmpty()) {
                    val yyyy = it.substring(0, 4)
                    val MM = it.substring(4, 6)
                    val dd = it.substring(6, 8)
                    SharedPreferenceUtil.setDate("$yyyy${MM.toInt()+1}$dd")
                }
            }
            return createFragment(1)
        }

        return when(SharedPreferenceUtil.getCurrentItem()) {
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
                SummaryFragment.getInstance()
            }
            else -> {
                DayFragment.getInstance()
            }
        }
    }
}