package com.example.accountbookuisampling.fragment.registerinput.consumption

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
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.consumption.ConsumptionInputTextRVAdapter
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.income.IncomeInputTextRVAdapter
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterConsumptionInputTextFragment(
    private val parentBinding: FragmentConsumptionBinding,
    private val categoryFlag: Int,
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

        binding = when (categoryFlag) {
            FLAG_ASSET -> FragmentRegisterAssetInputBinding.inflate(inflater, container, false)
            FLAG_CATEGORY -> FragmentRegisterCategoryInputBinding.inflate(
                inflater,
                container,
                false
            )
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
        when (categoryFlag) {
            FLAG_ASSET -> {
                setAssetView()
            }
            FLAG_CATEGORY -> {
                setCategoryView()
            }
        }
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
        _binding.recyclerView.layoutManager = getLayoutManager(INPUT_ITEM_VIEW_SPAN_COUNT)
        _binding.recyclerView.adapter = ConsumptionInputTextRVAdapter(assetList, parentBinding, this, categoryFlag)
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
        _binding.recyclerView.layoutManager = getLayoutManager(INPUT_ITEM_VIEW_SPAN_COUNT)
        _binding.recyclerView.adapter = ConsumptionInputTextRVAdapter(categoryList, parentBinding, this, categoryFlag)
    }

    fun openAddTextInputActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            AddTextInputActivity::class.java
        )
        intent.putExtra("flag", categoryFlag)

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
                    when (categoryFlag) {
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

        when (categoryFlag) {
            FLAG_ASSET -> {
                parentBinding.etAsset.clearFocus()
            }
            FLAG_CATEGORY -> {
                parentBinding.etCategory.clearFocus()
            }
        }
    }
}