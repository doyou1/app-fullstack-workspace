package com.example.custominputlayoutsampling

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputAmountBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputAssetBinding


class InputAmountFragment(private val parentBinding: ActivityMainBinding) : Fragment() {

    private lateinit var binding: FragmentInputAmountBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentBinding.etAmount.requestFocus()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputAmountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnAmount.setOnClickListener {
            parentBinding.etAmount.clearFocus()
            parentBinding.isShowAmount = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(binding: ActivityMainBinding) = InputAmountFragment(binding)
    }
}