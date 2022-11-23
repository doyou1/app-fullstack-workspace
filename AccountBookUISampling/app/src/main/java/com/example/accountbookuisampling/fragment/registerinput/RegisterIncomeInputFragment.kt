package com.example.accountbookuisampling.fragment.registerinput

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.activity.AddTextInputActivity
import com.example.accountbookuisampling.adapter.recyclerview.IncomeInputCalculateRVAdapter
import com.example.accountbookuisampling.adapter.recyclerview.IncomeInputTextRVAdapter
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterIncomeInputFragment(
    private val parentBinding: FragmentIncomeBinding,
    private val flag: Int
) : BottomSheetDialogFragment() {

    private lateinit var binding: ViewDataBinding
    private val TAG = this::class.java.simpleName

    private val addTextInputActivityResultLauncher = getAddTextInputActivityResultLauncher()

    private val assetList = arrayListOf(
        "현금",
        "은행",
        "카드",
        "추가"
    )

    private val categoryList = arrayListOf(
        "현금",
        "은행",
        "카드",
        "추가"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = when (flag) {
            FLAG_DATE -> FragmentRegisterDateInputBinding.inflate(inflater, container, false)
            FLAG_ASSET -> FragmentRegisterAssetInputBinding.inflate(inflater, container, false)
            FLAG_CATEGORY -> FragmentRegisterCategoryInputBinding.inflate(
                inflater,
                container,
                false
            )
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
        when (flag) {
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
        val _binding = binding as FragmentRegisterDateInputBinding
        // set title text
        _binding.tvTitle.text = TEXT_DATE

        // set click event
        _binding.btnClose.setOnClickListener {
            dismiss()
        }

        // set recyclerview layout like grid view
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }
//        _binding.recyclerView.adapter =
//            IncomeInputCalendarRVAdapter(assetList, parentBinding, this, flag)

    }

    private fun setAssetView() {
        val _binding = binding as FragmentRegisterAssetInputBinding

        // set title text
        _binding.tvTitle.text = TEXT_ASSET

        // set click event
        _binding.btnClose.setOnClickListener {
            dismiss()
        }

        // set recycler view

        // set recyclerview layout like grid view
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        _binding.recyclerView.adapter =
            IncomeInputTextRVAdapter(assetList, parentBinding, this, flag)
    }

    private fun setCategoryView() {
        val _binding = binding as FragmentRegisterCategoryInputBinding

        // set title text
        _binding.tvTitle.text = TEXT_CATEGORY

        // set click event
        _binding.btnClose.setOnClickListener {
            dismiss()
        }

        // set recycler view

        // set recyclerview layout like grid view
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        _binding.recyclerView.adapter =
            IncomeInputTextRVAdapter(categoryList, parentBinding, this, flag)
    }

    private fun setAmountView() {
        val _binding = binding as FragmentRegisterAmountInputBinding
        _binding.tvTitle.text = TEXT_AMOUNT
        _binding.etInput.setText(parentBinding.etAmount.text.toString())
        _binding.etInput.showSoftInputOnFocus = false

        _binding.btnClose.setOnClickListener {
            dismiss()
        }

        // set recyclerview layout like grid view
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                _binding.recyclerView.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        _binding.recyclerView.adapter =
            IncomeInputCalculateRVAdapter(CALCULATOR_ITEM_LIST, parentBinding, _binding, this)

    }

    fun openAddTextInputActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            AddTextInputActivity::class.java
        )
        intent.putExtra("flag", flag)

        addTextInputActivityResultLauncher.launch(
            intent
        )
    }

    private fun getAddTextInputActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    when (flag) {
                        FLAG_ASSET -> {
                            val _binding = binding as FragmentRegisterAssetInputBinding
                            val _adapter = _binding.recyclerView.adapter as IncomeInputTextRVAdapter

                            assetList.add(assetList.size - 1, data.getStringExtra("item")!!)
                            _adapter.notifyDataSetChanged()
                        }
                        FLAG_CATEGORY -> {
                            val _binding = binding as FragmentRegisterCategoryInputBinding
                            val _adapter = _binding.recyclerView.adapter as IncomeInputTextRVAdapter

                            categoryList.add(categoryList.size - 1, data.getStringExtra("item")!!)
                            _adapter.notifyDataSetChanged()
                        }
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        when (flag) {
            FLAG_DATE -> {
                parentBinding.etDate.clearFocus()
            }
            FLAG_ASSET -> {
                parentBinding.etAsset.clearFocus()
            }
            FLAG_CATEGORY -> {
                parentBinding.etCategory.clearFocus()
            }
            FLAG_AMOUNT -> {
                parentBinding.etAmount.clearFocus()
            }
        }
    }
}