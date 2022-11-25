package com.example.accountbookuisampling.fragment.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.recyclerview.main.CalendarRVAdapter
import com.example.accountbookuisampling.databinding.FragmentCalendarBinding
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.util.*
import java.util.ArrayList
import kotlin.random.Random

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView()
    }

    private fun setRecyclerView() {

        val contentList = arrayListOf<CalendarItem>()
        // 현재 달의 1일의 요일
        val dateList = DateUtil.getDateList()
        for (strDate in dateList) {
            val consumption = Random.nextInt(0, 100000)
            val income = Random.nextInt(0, 100000)
            val result = income - consumption
            contentList.add(
                CalendarItem(
                    TYPE_CALENDAR_CONTENT,
                    FLAG_NOT_CALENDAR_HEAD,
                    strDate,
                    income,
                    consumption,
                    result
                )
            )
        }

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                binding.rvList.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.rvList.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        val list = ArrayList<CalendarItem>()
        list.addAll(CALENDAR_HEAD_LIST)
        list.addAll(contentList)
        binding.rvList.adapter = CalendarRVAdapter(list)
    }

    companion object {
        private var instance: CalendarFragment? = null
        @JvmStatic
        fun getInstance() : CalendarFragment {
            if(instance == null) {
                instance = CalendarFragment()
                return instance as CalendarFragment
            }

            return instance as CalendarFragment
        }
    }
}