package com.example.accountbookuisampling.fragment.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.activity.MainActivity
import com.example.accountbookuisampling.adapter.recyclerview.main.HistoryRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentDayBinding
import com.example.accountbookuisampling.util.TEMP_DAY_VIEW_MODEL_LIST
import com.example.accountbookuisampling.viewmodel.DayViewModel

class DayFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentDayBinding
    private val TAG = this::class.java.simpleName
    private val _list = ArrayList<DayViewModel>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setData()
        setRecyclerView()
        setClickEvent()
    }

    private fun setData() {
        val list = (requireActivity().application as BaseApplication).historyDao.getByDate(currentDate)
        Log.e(TAG, "list: ${list}")
        (requireActivity() as MainActivity).updateSummary(1000, 2000, 1000 - 2000)

        // 要確認
        // 현재 yyyyMMdd의 데이터만 가져오기
        // history categoryId에 맞는 textCategory 가져오기 (category table join 필요)
        // historyType(수입, 지출)에 따른 amount text 색 변경

        // convert Entity to ViewModel
//        list.forEach { history ->
//            val day = history.date.substring(6, 8)
//            val yearMonth = history.date.substring(0, 6)
//            val time = history.date.substring(8, 12)
//            _list.add(
//                DayViewModel(
//                    history.id,
//                    day,
//                    yearMonth,
//                    DateUtil.getStringDayOfWeek(history.date),
//                    history.categoryId.toString(),
//                    history.detail,
//                    DateUtil.getStringTime(time),
//                    history.historyType,
//                    history.amount.toString()
//                )
//            )
//        }

        _list.addAll(TEMP_DAY_VIEW_MODEL_LIST)
    }

    private fun setRecyclerView() {
        if(_list.isEmpty()) {
            binding.rvList.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
            return
        }
        setLayoutManager()
        binding.rvList.adapter = HistoryRVAdapter(_list)
    }

    private fun setLayoutManager() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                GridLayoutManager(context, 2)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager(context)
            }
            else -> throw NotImplementedError()
        }

        binding.rvList.layoutManager = layoutManager
    }

    private fun setClickEvent() {

    }

    companion object {
//        private var instance: DayFragment? = null
//        @JvmStatic
//        fun getInstance() : DayFragment {
//            if(instance == null) {
//                instance = DayFragment()
//                return instance as DayFragment
//            }
//
//            return instance as DayFragment
//        }
    }
}