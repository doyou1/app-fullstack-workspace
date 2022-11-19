package com.example.accountbookuisampling.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.HistoryRVAdapter
import com.example.accountbookuisampling.databinding.FragmentDayBinding
import com.example.accountbookuisampling.dataclass.History

class DayFragment : Fragment() {

    private lateinit var binding: FragmentDayBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView()
        setClickEvent()
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

        val adapter = HistoryRVAdapter(ArrayList<History>())

        binding.rvList.adapter = adapter
    }

    private fun setClickEvent() {
//        binding.btnAddHistory.setOnClickListener {
//            val _adapter: HistoryRVAdapter = binding.rvList.adapter as HistoryRVAdapter
//
//            val startIdx = _adapter.itemCount
//            _adapter.addHistory(History("202009201511", "숙소비", "M카드", "1700000"))
//            _adapter.notifyItemRangeChanged(startIdx, startIdx+1)
//        }
    }
    companion object {
        private var instance: DayFragment? = null
        @JvmStatic
        fun newInstance() : DayFragment {
            if(instance == null) {
                instance = DayFragment()
                return instance as DayFragment
            }

            return instance as DayFragment
        }
    }
}