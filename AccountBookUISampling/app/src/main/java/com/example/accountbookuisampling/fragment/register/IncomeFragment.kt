package com.example.accountbookuisampling.fragment.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.accountbookuisampling.databinding.FragmentIncomeBinding
import com.example.accountbookuisampling.fragment.registerinput.RegisterIncomeInputFragment
import com.example.accountbookuisampling.util.*

class IncomeFragment : Fragment() {

    private lateinit var binding: FragmentIncomeBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        disableKeyboard()
        setClickEvent()
    }


    private fun setClickEvent() {
        // 날짜, Date, 日付
        binding.etDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) RegisterIncomeInputFragment(binding, FLAG_DATE).show(
                requireActivity().supportFragmentManager,
                TAG_DATE
            )
        }

        // 자산, Asset,
        binding.etAsset.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) RegisterIncomeInputFragment(binding, FLAG_ASSET).show(
                requireActivity().supportFragmentManager,
                TAG_ASSET
            )
        }

        // 분류, Category, 分類
        binding.etCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) RegisterIncomeInputFragment(binding, FLAG_CATEGORY).show(
                requireActivity().supportFragmentManager,
                TAG_CATEGORY
            )
        }

        // 금액, Amount, 金額
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) RegisterIncomeInputFragment(binding, FLAG_AMOUNT).show(
                requireActivity().supportFragmentManager,
                TAG_AMOUNT
            )

        }

        // 내용, Detail, 内容
        // not need Custom Input
//        binding.etDetail.setOnClickListener {
//
//        }

        //  추가내용, Detail, 追加内容
        // not need Custom Input
//        binding.etAdditionDetail.setOnFocusChangeListener { _, hasFocus ->
//
//        }

        binding.btnDetailOnoff.setOnClickListener {
            binding.isImportant = !binding.isImportant
        }

        binding.layoutWrap.setOnClickListener {
            hideKeyboard()
        }

    }

    private fun disableKeyboard() {
        binding.etDate.showSoftInputOnFocus = false
        binding.etAsset.showSoftInputOnFocus = false
        binding.etCategory.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false

        // not need Custom Input
//        binding.etDetail.showSoftInputOnFocus = false
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

    companion object {
        private var instance: IncomeFragment? = null

        @JvmStatic
        fun getInstance(): IncomeFragment {
            if (instance == null) {
                instance = IncomeFragment()
                return instance as IncomeFragment
            }

            return instance as IncomeFragment
        }
    }
}