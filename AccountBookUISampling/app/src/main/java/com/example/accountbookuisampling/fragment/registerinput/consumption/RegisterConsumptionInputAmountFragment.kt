package com.example.accountbookuisampling.fragment.registerinput.consumption

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.consumption.ConsumptionInputCalculateRVAdapter
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.income.IncomeInputCalculateRVAdapter
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterConsumptionInputAmountFragment(
    private val parentBinding: FragmentConsumptionBinding,
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRegisterAmountInputBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterAmountInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    // config layout full size
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }


    override fun onResume() {
        super.onResume()
        setAmountView()
    }

    private fun setAmountView() {
        binding.tvTitle.text = TEXT_AMOUNT
        binding.etInput.setText(parentBinding.etAmount.text.toString())
        binding.etInput.showSoftInputOnFocus = false

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.recyclerView.layoutManager = getLayoutManager(INPUT_ITEM_VIEW_SPAN_COUNT)
        binding.recyclerView.adapter =
            ConsumptionInputCalculateRVAdapter(CALCULATOR_ITEM_LIST, parentBinding, binding, this)

    }

    private fun getLayoutManager(span: Int): GridLayoutManager {
        // set recyclerview layout like grid view
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), span)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), span)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentBinding.etAmount.clearFocus()
    }
}