package com.example.accountbookuisampling.fragment.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.recyclerview.main.WeekRVAdapter
import com.example.accountbookuisampling.databinding.FragmentWeekBinding
import com.example.accountbookuisampling.dataclass.Week
import java.util.ArrayList

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
        private var instance: WeekFragment? = null
        @JvmStatic
        fun getInstance() : WeekFragment {
            if(instance == null) {
                instance = WeekFragment()
                return instance as WeekFragment
            }

            return instance as WeekFragment
        }
    }


}