package com.example.accountbookuisampling.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.CalendarRVAdapter
import com.example.accountbookuisampling.databinding.FragmentCalendarBinding
import com.example.accountbookuisampling.util.DateUtil
import com.example.accountbookuisampling.util.TYPE_CALENDAR_CONTENT
import com.example.accountbookuisampling.util.TYPE_CALENDAR_HEAD
import com.example.accountbookuisampling.dataclass.CalendarItem
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

//        // layout_content height =  전체 height - layout_summary height
//        val layoutContentHeight = binding.root.height - binding.layoutSummary.height - 1
//        binding.layoutContent.layoutParams.height = layoutContentHeight

        // head(7) + content(35)
        val headList = arrayListOf<CalendarItem>(
            CalendarItem(TYPE_CALENDAR_HEAD, 0,"", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, 1,"", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, 2,"", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, 3,"", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, 4,"", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, 5,"", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, 6,"", 0, 0, 0)
        )

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
                    -1,
                    strDate,
                    income,
                    consumption,
                    result
                )
            )
        }

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), 7)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                binding.rvList.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), 7)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.rvList.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        val list = ArrayList<CalendarItem>()
        list.addAll(headList)
        list.addAll(contentList)
        binding.rvList.adapter = CalendarRVAdapter(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalendarFragment()
    }
}