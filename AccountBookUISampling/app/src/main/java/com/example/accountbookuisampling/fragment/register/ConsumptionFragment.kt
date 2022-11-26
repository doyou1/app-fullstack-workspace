package com.example.accountbookuisampling.fragment.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.activity.SelectInstallmentActivity
import com.example.accountbookuisampling.activity.SelectRepeatActivity
import com.example.accountbookuisampling.databinding.FragmentConsumptionBinding
import com.example.accountbookuisampling.fragment.registerinput.consumption.RegisterConsumptionInputAmountFragment
import com.example.accountbookuisampling.fragment.registerinput.consumption.RegisterConsumptionInputDateFragment
import com.example.accountbookuisampling.fragment.registerinput.consumption.RegisterConsumptionInputTextFragment
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConsumptionFragment : Fragment() {

    private lateinit var binding: FragmentConsumptionBinding
    private val TAG = this::class.java.simpleName
    private var inputFragment: BottomSheetDialogFragment? = null

    private val selectRepeatActivityResultLauncher = getSelectRepeatActivityResultLauncher()
    private val selectInstallmentActivityResultLauncher = getSelectInstallmentActivityResultLauncher()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConsumptionBinding.inflate(inflater, container, false)

        binding.isImportant = false

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
                inputFragment = RegisterConsumptionInputDateFragment(binding)
                inputFragment?.show(
                    requireActivity().supportFragmentManager,
                    TAG_DATE
                )
            }
        }

        // 자산, Asset,
        binding.etAsset.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputFragment = RegisterConsumptionInputTextFragment(binding, FLAG_ASSET)
                inputFragment?.show(
                    requireActivity().supportFragmentManager,
                    TAG_ASSET
                )
            }
        }

        // 분류, Category, 分類
        binding.etCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputFragment = RegisterConsumptionInputTextFragment(binding, FLAG_CATEGORY)
                inputFragment?.show(
                    requireActivity().supportFragmentManager,
                    TAG_CATEGORY
                )
            }
        }

        // 금액, Amount, 金額
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) RegisterConsumptionInputAmountFragment(binding).show(
                requireActivity().supportFragmentManager,
                TAG_AMOUNT
            )
        }

        binding.etDetail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) showKeyboard()
        }
    }

    private fun setClickEvent() {
        binding.btnDetailOnoff.setOnClickListener {
            binding.isImportant = !binding.isImportant
        }

        binding.layoutWrap.setOnClickListener {
            hideKeyboard()
        }

        binding.btnRepeat.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.btnRepeat)
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.select_repeat -> {
                        openSelectRepeatActivityResultLauncher()
                        true
                    }
                    R.id.select_installment -> {
                        openSelectInstallmentActivityResultLauncher()
                        true
                    }
                }
                false
            }
            popup.menuInflater.inflate(R.menu.menu_select_repeat_and_installment, popup.menu)
            popup.show()
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
                    Log.e(TAG, "data.getStringExtra(\"item\"): ${data.getStringExtra("item")}")
                    

                }
            }
        }
    }

    private fun openSelectInstallmentActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            SelectInstallmentActivity::class.java
        )
        selectInstallmentActivityResultLauncher.launch(
            intent
        )
    }

    private fun getSelectInstallmentActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    Log.e(TAG, "data.getStringExtra(\"item\"): ${data.getStringExtra("item")}")
                }
            }
        }
    }


    companion object {
        private var instance: ConsumptionFragment? = null

        @JvmStatic
        fun getInstance(): ConsumptionFragment {
            if (instance == null) {
                instance = ConsumptionFragment()
                return instance as ConsumptionFragment
            }

            return instance as ConsumptionFragment
        }
    }
}