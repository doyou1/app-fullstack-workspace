package com.example.accountbookuisampling.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.adapter.CalendarRVAdapter
import com.example.accountbookuisampling.adapter.HistoryRVAdapter
import com.example.accountbookuisampling.adapter.WeekRVAdapter
import com.example.accountbookuisampling.databinding.FragmentDayBinding
import com.example.accountbookuisampling.databinding.FragmentWeekBinding
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.dataclass.History
import com.example.accountbookuisampling.dataclass.Week
import com.example.accountbookuisampling.util.DateUtil
import com.example.accountbookuisampling.util.TYPE_CALENDAR_CONTENT
import com.example.accountbookuisampling.util.TYPE_CALENDAR_HEAD
import java.util.ArrayList
import kotlin.random.Random

class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                GridLayoutManager(context, 2)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager(context)
            } else -> throw NotImplementedError()
        }

        binding.rvList.layoutManager = layoutManager

        val list : ArrayList<Week> = arrayListOf(
            Week("12/25~12/31", 0, 0, 0),
            Week("12/18~12/24", 0, 0, 0),
            Week("12/11~12/17", 0, 0, 0),
            Week("12/04~12/10", 0, 0, 0),
            Week("11/27~12/03", 0, 0, 0)
        )
        binding.rvList.adapter = WeekRVAdapter(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeekFragment()
    }
}