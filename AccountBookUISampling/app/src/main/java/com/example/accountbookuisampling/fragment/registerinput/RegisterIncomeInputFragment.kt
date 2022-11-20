package com.example.accountbookuisampling.fragment.registerinput

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.IncomeInputTextRVAdapter
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.util.FLAG_AMOUNT
import com.example.accountbookuisampling.util.FLAG_ASSET
import com.example.accountbookuisampling.util.FLAG_CATEGORY
import com.example.accountbookuisampling.util.FLAG_DATE
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterIncomeInputFragment(
    private val parentBinding: FragmentIncomeBinding,
    private val flag: Int
) : BottomSheetDialogFragment() {

    private lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = when(flag) {
            FLAG_DATE -> FragmentRegisterDateInputBinding.inflate(inflater, container, false)
            FLAG_ASSET -> FragmentRegisterAssetInputBinding.inflate(inflater, container, false)
            FLAG_CATEGORY -> FragmentRegisterCategoryInputBinding.inflate(inflater, container, false)
            FLAG_AMOUNT -> FragmentRegisterAmountInputBinding.inflate(inflater, container, false)
            else -> throw NotImplementedError()
        }
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

        when(flag) {
            FLAG_DATE -> {
                setDateView()

            }
            FLAG_ASSET -> {
                setAssetView()
            }
            FLAG_CATEGORY -> {
                setCategoryView()
            }
            FLAG_AMOUNT -> {
                setAmountView()

            }
        }
    }

    private fun setDateView() {
//        val _binding = binding as FragmentRegisterDateInputBinding
//        _binding.tvTitle.text = "날짜"

    }
    private fun setAssetView() {
        val _binding = binding as FragmentRegisterAssetInputBinding

        // set title text
        _binding.tvTitle.text = "자산"

        // set click event
        _binding.btnClose.setOnClickListener {
            parentBinding.etAsset.clearFocus()
            dismiss()
        }

        // set recycler view

        // set recyclerview layout like grid view
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), 4)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), 4)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        val list = arrayListOf(
            "현금",
            "은행",
            "카드"
        )
        list.add("추가")
        _binding.recyclerView.adapter = IncomeInputTextRVAdapter(list, parentBinding, this, flag)

    }
    private fun setCategoryView() {
//        val _binding = binding as FragmentRegisterCategoryInputBinding
//        _binding.tvTitle.text = "분류"

//        _binding.btnClose.setOnClickListener {
//            dismiss()
//        }
    }
    private fun setAmountView() {
//        val _binding = binding as FragmentRegisterAmountInputBinding
//        _binding.tvTitle.text = "금액"

//        _binding.btnClose.setOnClickListener {
//            dismiss()
//        }
    }

    override fun dismiss() {
        super.dismiss()
    }


}