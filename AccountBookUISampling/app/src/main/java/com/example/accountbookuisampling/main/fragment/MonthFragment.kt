package com.example.accountbookuisampling.main.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.main.adapter.MonthRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentMonthBinding
import com.example.accountbookuisampling.util.MONTH_VIEW_MODEL_LIST
import com.example.accountbookuisampling.util.TEXT_FIRST_DAY_OF_MONTH
import com.example.accountbookuisampling.main.viewmodel.MonthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MonthFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentMonthBinding
    private val TAG = this::class.java.simpleName

    private val _list = ArrayList<MonthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setData()
        setRecyclerView()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val currentYear = currentDate?.substring(0, 4)
            val list =
                (requireActivity().application as BaseApplication).historyDao.getByDate(currentYear)
            lifecycleScope.launch(Dispatchers.Main) {
                (requireActivity() as MainActivity).updateSummary(1, 2, 1 - 2)
            }
        }

        // 要確認
        // 현재 yyyy의 데이터를 월별로 가져오기
        // detail 등 세부 데이터는 별로없고, '월'별로 수입, 지출 총합만 있으면 된다. (group by 필요)
    }

    private fun setRecyclerView() {
        Log.e(TAG, "setRecyclerView")
        val list = ArrayList<MonthViewModel>()
        if (_list.isEmpty()) {
            list.addAll(getEmptyMonthViewModelList())
        } else {
            list.addAll(_list)
        }

        setLayoutManager()

        binding.rvList.adapter = MonthRVAdapter(list)
    }

    private fun getEmptyMonthViewModelList(): ArrayList<MonthViewModel> {
        if (currentDate?.isEmpty() == true) {
            return MONTH_VIEW_MODEL_LIST
        } else {
            var emptyList = MONTH_VIEW_MODEL_LIST
            val currentYear = currentDate!!.substring(0, 4)
            val cal = Calendar.getInstance()
            cal.set(currentYear.toInt(), 0, 1)
            val tempList = ArrayList<MonthViewModel>()
            emptyList.forEach { item ->
                val month = String.format("%02d", cal.get(Calendar.MONTH) + 1)
                val firstDay = TEXT_FIRST_DAY_OF_MONTH
                val lastDay = cal.getActualMaximum(Calendar.DATE)
                val period = "$month/$firstDay~$month/$lastDay"

                tempList.add(
                    MonthViewModel(
                        month,
                        period,
                        item.income,
                        item.consumption,
                        item.sum
                    )
                )

                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1)
            }
            return tempList
        }
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

    companion object {
//        private var instance: MonthFragment? = null
//        @JvmStatic
//        fun getInstance() : MonthFragment {
//            if(instance == null) {
//                instance = MonthFragment()
//                return instance as MonthFragment
//            }
//
//            return instance as MonthFragment
//        }
    }
}