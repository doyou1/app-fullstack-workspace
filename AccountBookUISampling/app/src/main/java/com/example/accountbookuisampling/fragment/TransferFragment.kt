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
import com.example.accountbookuisampling.databinding.FragmentConsumptionBinding
import com.example.accountbookuisampling.databinding.FragmentDayBinding
import com.example.accountbookuisampling.databinding.FragmentIncomeBinding
import com.example.accountbookuisampling.databinding.FragmentTransferBinding
import com.example.accountbookuisampling.dataclass.History

class TransferFragment : Fragment() {

    private lateinit var binding: FragmentTransferBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransferBinding.inflate(inflater, container, false)
        binding.isImportant = false
        binding.isShowFee = false

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnAmountFee.setOnClickListener {
            binding.isShowFee = true
        }

        binding.btnCloseFee.setOnClickListener {
            binding.isShowFee = false
        }

        binding.btnDetailOnoff.setOnClickListener {
            binding.isImportant = !binding.isImportant
        }
    }
    companion object {
        private var instance: TransferFragment? = null
        @JvmStatic
        fun newInstance() : TransferFragment {
            if(instance == null) {
                instance = TransferFragment()
                return instance as TransferFragment
            }

            return instance as TransferFragment
        }
    }
}