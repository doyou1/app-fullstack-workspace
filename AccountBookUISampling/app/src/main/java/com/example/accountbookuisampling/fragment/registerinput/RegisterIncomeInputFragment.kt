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
import com.example.accountbookuisampling.adapter.recyclerview.IncomeInputDateRVAdapter
import com.example.accountbookuisampling.adapter.recyclerview.IncomeInputTextRVAdapter
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.dataclass.DateItem
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

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

    // yyyy/MM/DD
    private var date = parentBinding.etDate.text.toString().substring(0, 10)

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

    fun changeDate(value: String) {
        date = value
        parentBinding.etDate.setText(DateUtil.getDateText(date))
        setDateView()
    }

    private fun setDateView() {
        val _binding = binding as FragmentRegisterDateInputBinding
        // set title text
        _binding.tvTitle.text = TEXT_DATE

        _binding.tvMonth.text = "${DateUtil.getMonth(date)}$TEXT_MONTH"
        // set click event
        _binding.btnClose.setOnClickListener {
            dismiss()
        }
        _binding.btnLeft.setOnClickListener {

        }
        _binding.btnRight.setOnClickListener {

        }
        _binding.btnToday.setOnClickListener {
            changeDate(DateUtil.getTodayText().substring(0, 10))
        }

        _binding.recyclerView.layoutManager = getLayoutManager(CALENDAR_VIEW_SPAN_COUNT)

        val dateList = DateUtil.getDateList(date)
        val contentList = ArrayList<DateItem>()
        dateList.forEachIndexed { index, date ->
            contentList.add(
                DateItem(TYPE_CALENDAR_CONTENT,index % 7,date)
            )
        }

        val list = ArrayList<DateItem>()
        list.addAll(DATE_HEAD_LIST)
        list.addAll(contentList)

        _binding.recyclerView.adapter =
            IncomeInputDateRVAdapter(date, list, parentBinding, this)
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
        _binding.recyclerView.layoutManager = getLayoutManager(INPUT_ITEM_VIEW_SPAN_COUNT)
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

        _binding.recyclerView.layoutManager = getLayoutManager(INPUT_ITEM_VIEW_SPAN_COUNT)
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