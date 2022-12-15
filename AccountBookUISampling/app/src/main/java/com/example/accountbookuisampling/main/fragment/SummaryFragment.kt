package com.example.accountbookuisampling.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.accountbookuisampling.databinding.FragmentSummaryBinding
import com.example.accountbookuisampling.main.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        setData()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val currentYearMonth = currentDate?.substring(0, 6)
//            val list =
//                (requireActivity().application as BaseApplication).historyDao.getByDate(
//                    currentYearMonth
//                )
            lifecycleScope.launch(Dispatchers.Main) {
                (requireActivity() as MainActivity).updateSummary(222, 333, 222 - 333)
            }
        }
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