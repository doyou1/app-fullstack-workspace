package com.example.accountbookuisampling.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.accountbookuisampling.fragment.register.ConsumptionFragment
import com.example.accountbookuisampling.fragment.register.IncomeFragment
import com.example.accountbookuisampling.fragment.register.TransferFragment
import com.example.accountbookuisampling.util.REGISTER_FRAGMENT_COUNT

class RegisterViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return REGISTER_FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                IncomeFragment.getInstance()
            }
            1 -> {
                ConsumptionFragment.getInstance()
            }
            2 -> {
                TransferFragment.getInstance()
            }
            else -> {
                IncomeFragment.getInstance()
            }
        }
    }
}