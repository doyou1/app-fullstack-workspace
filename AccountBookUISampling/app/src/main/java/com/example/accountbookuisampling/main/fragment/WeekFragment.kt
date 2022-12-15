package com.example.accountbookuisampling.main.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.main.adapter.WeekRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentWeekBinding
import com.example.accountbookuisampling.util.TEMP_WEEK_VIEW_MODEL_LIST
import com.example.accountbookuisampling.main.viewmodel.WeekViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class WeekFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    private val TAG = this::class.java.simpleName
    private val _list = ArrayList<WeekViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setData()
        setRecyclerView()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val currentYearMonth = currentDate?.substring(0, 6)
//            val list =
//                (requireActivity().application as BaseApplication).historyDao.getByDate(
//                    currentYearMonth
//                )
            lifecycleScope.launch(Dispatchers.Main) {
                (requireActivity() as MainActivity).updateSummary(300, 200, 300 - 200)
            }
        }

        // 要確認
        // 현재 yyyyMM의 데이터를 주별로 가져오기
        // detail 등 세부 데이터는 필요없고, '주'별로 수입, 지출, 총합만 있으면 된다. (group by 필요)

    }

    private fun setRecyclerView() {
        setLayoutManager()
        _list.addAll(TEMP_WEEK_VIEW_MODEL_LIST)
        binding.rvList.adapter = WeekRVAdapter(_list)
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
//        private var instance: WeekFragment? = null
//        @JvmStatic
//        fun getInstance() : WeekFragment {
//            if(instance == null) {
//                instance = WeekFragment()
//                return instance as WeekFragment
//            }
//
//            return instance as WeekFragment
//        }
    }

}