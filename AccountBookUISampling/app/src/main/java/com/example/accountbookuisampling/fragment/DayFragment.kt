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
import com.example.accountbookuisampling.adapter.RVAdapter
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

        val list = ArrayList<History>()

        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))
        list.add(History("202009201511", "숙소비", "M카드", "1700000"))

        val adapter = RVAdapter(list)

        binding.rvList.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = DayFragment()
    }
}