package com.example.accountbookuisampling.fragment.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.activity.MainActivity
import com.example.accountbookuisampling.adapter.recyclerview.main.CalendarRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentCalendarBinding
import com.example.accountbookuisampling.util.*
import com.example.accountbookuisampling.viewmodel.CalendarViewModel
import java.util.ArrayList
import kotlin.random.Random

class CalendarFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val TAG = this::class.java.simpleName

    private val _list = ArrayList<CalendarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        setData()
        setRecyclerView()
    }

    private fun setData() {
        val currentYearMonth = currentDate?.substring(0, 6)
        val list =
            (requireActivity().application as BaseApplication).historyDao.getByDate(currentYearMonth)
        Log.e(TAG, "list: ${list}")

        (requireActivity() as MainActivity).updateSummary(1500, 3000, 1500 - 3000)

        // 要確認
        // 현재 yyyyMM의 데이터를 일별로 가져오기
        // detail 등 세부 데이터는 필요없고, '일'별로 수입, 지출, 총합만 있으면 된다. (group by 필요)

//        list.forEach { history ->
////            _list.add()
//        }

    }

    private fun setRecyclerView() {
        val list = ArrayList<CalendarViewModel>()
        if (_list.isEmpty()) {
            list.addAll(CALENDAR_HEAD_LIST)
            list.addAll(getEmptyContentList())
        } else {
            list.addAll(CALENDAR_HEAD_LIST)
            list.addAll(_list)
        }

        setLayoutManager()
        // waiting refresh for measuredHeight is 0
        binding.rvList.post {
            binding.rvList.minimumHeight = binding.rvList.measuredHeight
            binding.rvList.adapter = CalendarRVAdapter(list)
        }
    }

    private fun setLayoutManager() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(context, CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    GridLayoutManager.HORIZONTAL
                binding.rvList.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(context, CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.rvList.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }
    }

    private fun getEmptyContentList(): ArrayList<CalendarViewModel> {
        val list = ArrayList<CalendarViewModel>()
        val currentYearMonth = currentDate?.substring(0, 6)
        val dateList = DateUtil.getDateList(currentYearMonth)

        dateList.forEach { date ->
            list.add(
                CalendarViewModel(
                    TYPE_CALENDAR_CONTENT,
                    date,
                    "",
                    "",
                    ""
                )
            )
        }

        return list
    }

    companion object {
//        private var instance: CalendarFragment? = null
//        @JvmStatic
//        fun getInstance() : CalendarFragment {
//            if(instance == null) {
//                instance = CalendarFragment()
//                return instance as CalendarFragment
//            }
//
//            return instance as CalendarFragment
//        }
    }
}