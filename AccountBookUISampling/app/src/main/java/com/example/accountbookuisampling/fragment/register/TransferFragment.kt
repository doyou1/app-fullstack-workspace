package com.example.accountbookuisampling.fragment.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.activity.SelectRepeatActivity
import com.example.accountbookuisampling.databinding.FragmentTransferBinding
import com.example.accountbookuisampling.fragment.registerinput.transfer.RegisterTransferInputAmountFragment
import com.example.accountbookuisampling.fragment.registerinput.transfer.RegisterTransferInputDateFragment
import com.example.accountbookuisampling.fragment.registerinput.transfer.RegisterTransferInputTextFragment
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TransferFragment : Fragment() {

    private lateinit var binding: FragmentTransferBinding
    private val TAG = this::class.java.simpleName
    private var inputFragment: BottomSheetDialogFragment? = null
    private val selectRepeatActivityResultLauncher = getSelectRepeatActivityResultLauncher()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransferBinding.inflate(inflater, container, false)
        binding.isImportant = false
        binding.isRepeat = false
        binding.isShowFee = false

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setFocusChangeEvent()
        setClickEvent()
        disableKeyboard()

        binding.etDate.setText(DateUtil.getTodayText())
        binding.etAsset.requestFocus()
    }

    private fun setFocusChangeEvent() {
        // 날짜, Date, 日付
        binding.etDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputFragment = RegisterTransferInputDateFragment(binding)
                inputFragment?.show(
                    requireActivity().supportFragmentManager,
                    TAG_DATE
                )
            }
        }

        // 자산, Asset,
        binding.etAsset.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputFragment = RegisterTransferInputTextFragment(binding, FLAG_ASSET)
                inputFragment?.show(
                    requireActivity().supportFragmentManager,
                    TAG_ASSET
                )
            }
        }

        // 분류, Category, 分類
        binding.etCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputFragment = RegisterTransferInputTextFragment(binding, FLAG_CATEGORY)
                inputFragment?.show(
                    requireActivity().supportFragmentManager,
                    TAG_CATEGORY
                )
            }
        }

        // 금액, Amount, 金額
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) RegisterTransferInputAmountFragment(binding).show(
                requireActivity().supportFragmentManager,
                TAG_AMOUNT
            )
        }

        binding.etDetail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) showKeyboard()
        }
    }

    private fun setClickEvent() {
        binding.btnAmountFee.setOnClickListener {
            binding.isShowFee = true
        }

        binding.btnCloseFee.setOnClickListener {
            binding.isShowFee = false
        }

        binding.btnRepeat.setOnClickListener {
            if (binding.isRepeat) {
                showRepeatAndCancelPopup()
            } else {
                openSelectRepeatActivityResultLauncher()
            }
        }

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
    }

    private fun showKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

    override fun onPause() {
        super.onPause()
        inputFragment?.dismiss()
    }

    private fun openSelectRepeatActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            SelectRepeatActivity::class.java
        )
        selectRepeatActivityResultLauncher.launch(
            intent
        )
    }

    private fun getSelectRepeatActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    when (val value = data.getStringExtra("item")) {
                        TEXT_EMPTY -> return@registerForActivityResult
                        TEXT_NONE -> return@registerForActivityResult
                        else -> {
                            binding.isRepeat = true
                            binding.textRepeat = value
                        }
                    }
                }
            }
        }
    }

    private fun showRepeatAndCancelPopup() {
        val popup = PopupMenu(requireContext(), binding.btnRepeat)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.select_repeat -> {
                    openSelectRepeatActivityResultLauncher()
                    true
                }
                R.id.select_cancel -> {
                    binding.isRepeat = false
                    true
                }
            }
            false
        }
        popup.menuInflater.inflate(R.menu.menu_select_repeat_and_cancel, popup.menu)
        popup.show()
    }

    companion object {
        private var instance: TransferFragment? = null

        @JvmStatic
        fun getInstance(): TransferFragment {
            if (instance == null) {
                instance = TransferFragment()
                return instance as TransferFragment
            }

            return instance as TransferFragment
        }
    }
}