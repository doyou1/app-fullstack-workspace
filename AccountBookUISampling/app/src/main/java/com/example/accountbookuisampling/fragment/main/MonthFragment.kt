package com.example.accountbookuisampling.fragment.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.accountbookuisampling.databinding.FragmentMonthBinding
import com.example.accountbookuisampling.dataclass.Month
import com.example.accountbookuisampling.viewmodel.MonthViewModel
import java.time.YearMonth
import kotlin.collections.HashMap
import kotlin.random.Random

class MonthFragment : Fragment() {

    private lateinit var binding: FragmentMonthBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setMonthList()
    }

    private fun setMonthList() {
        val map = HashMap<String, MonthViewModel>()
        for(i in 1..12) {
            val consumption = Random.nextInt(0, 100000)
            val income = Random.nextInt(0, 100000)
            val result = income - consumption
            val month = if(i < 10) "0$i"
                        else i.toString()
            val model = Month("2022$month", consumption, income, result)

            map[month] = formatToViewModel(model)
        }

        binding.map = map
    }

    private fun formatToViewModel(month: Month): MonthViewModel {
        val YYYY = month.month.substring(0, 4)
        val MM = month.month.substring(4, 6)

        val period = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yearMonth = YearMonth.of(YYYY.toInt(), MM.toInt())
            val lastDay = yearMonth.lengthOfMonth().toString()
            "$MM/01~$MM/$lastDay"
        } else {
            ""
        }

        return MonthViewModel(
            month.month,
            period,
            month.income.toString(),
            month.consumption.toString(),
            month.result.toString()
        )
    }

    companion object {
        private var instance: MonthFragment? = null
        @JvmStatic
        fun getInstance() : MonthFragment {
            if(instance == null) {
                instance = MonthFragment()
                return instance as MonthFragment
            }

            return instance as MonthFragment
        }
    }
}