package com.example.accountbookuisampling.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentSummaryBinding
import com.example.accountbookuisampling.util.SharedPreferenceUtil

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

//        SharedPreferenceUtil.getDate()?.let {
//            if(it.isNotEmpty()) {
//                binding.btnExportExcel.text = it
//            }
//        }
    }

    companion object {
        private var instance: SummaryFragment? = null
        @JvmStatic
        fun getInstance() : SummaryFragment {
            if(instance == null) {
                instance = SummaryFragment()
                return instance as SummaryFragment
            }

            return instance as SummaryFragment
        }
    }
}