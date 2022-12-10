package com.example.accountbookuisampling.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.accountbookuisampling.databinding.FragmentSummaryBinding

class SummaryFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
//        private var instance: SummaryFragment? = null
//
//        @JvmStatic
//        fun getInstance(): SummaryFragment {
//            if (instance == null) {
//                instance = SummaryFragment()
//                return instance as SummaryFragment
//            }
//
//            return instance as SummaryFragment
//        }
    }
}