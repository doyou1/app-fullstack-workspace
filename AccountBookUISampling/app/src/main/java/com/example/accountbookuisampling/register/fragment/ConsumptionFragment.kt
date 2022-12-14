package com.example.accountbookuisampling.register.fragment

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
import com.example.accountbookuisampling.registerinput.activity.SelectInstallmentActivity
import com.example.accountbookuisampling.registerinput.activity.SelectRepeatActivity
import com.example.accountbookuisampling.databinding.FragmentConsumptionBinding
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputAmountFragment
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputCategoryFragment
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputDateFragment
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class ConsumptionFragment : BaseRegisterFragment() {

    private lateinit var binding: FragmentConsumptionBinding
    private val TAG = this::class.java.simpleName
    private val selectRepeatActivityResultLauncher = getSelectRepeatActivityResultLauncher()
    private val selectInstallmentActivityResultLauncher = getSelectInstallmentActivityResultLauncher()
    override var currentView: Int = FLAG_CONSUMPTION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConsumptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableKeyboard()
        binding.isImportant = false
        binding.isRepeatOrInstallment = false
        binding.etDate.setText(DateUtil.getTodayText())
    }

    override fun onResume() {
        super.onResume()
        setFocusChangeEvent()
        setClickEvent()
    }

    private fun setFocusChangeEvent() {
        binding.etDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_DATE)
            }
        }
        // Focusの際、またクリックすると
        binding.etDate.setOnClickListener {
            if (binding.etDate.hasFocus() && binding.frameLayout.visibility == View.GONE) {
                binding.frameLayout.visibility == View.VISIBLE
            }
        }
        binding.etAsset.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_ASSET)
            }
        }
        binding.etCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_CATEGORY)
            }
        }
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_AMOUNT)
            }
        }
        binding.etDetail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.frameLayout.visibility == View.VISIBLE) binding.frameLayout.visibility =
                    View.GONE
            }
        }
    }

    private fun setClickEvent() {
        binding.btnDetailOnoff.setOnClickListener {
            binding.isImportant = !binding.isImportant
        }

        binding.btnRepeat.setOnClickListener {
            if(binding.isRepeatOrInstallment) {
                showRepeatAndInstallmentAndCancelPopup()
            } else {
                showRepeatAndInstallmentPopup()
            }
        }

        binding.layoutEditTextWrap.setOnClickListener {
            hideCurrentInputView()
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    private fun showInputFragment(flag: Int) {

        val transaction = childFragmentManager.beginTransaction()
        val fragment = when (flag) {
            FLAG_DATE -> {
                // yyyyMMdd
                RegisterInputDateFragment(binding.etDate.text.toString().substring(0, 8))
            }
            FLAG_ASSET -> {
                RegisterInputCategoryFragment()
            }
            FLAG_CATEGORY -> {
                RegisterInputCategoryFragment()
            }
            FLAG_AMOUNT -> {
                RegisterInputAmountFragment(binding.etAmount.text.toString())
            }
            else -> throw NotImplementedError()
        }

        transaction.replace(binding.frameLayout.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        // もし、キーボートが開いているとしたら
        if (binding.frameLayout.visibility == View.GONE) binding.frameLayout.visibility =
            View.VISIBLE
    }

    override fun changeDateFromChild(value: String) {
        binding.etDate.setText(value)
    }

    override fun closeInputLayout() {
        hideCurrentInputView()
    }

    private fun disableKeyboard() {
        binding.etDate.showSoftInputOnFocus = false
        binding.etAsset.showSoftInputOnFocus = false
        binding.etCategory.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun hideCurrentInputView() {
        binding.frameLayout.visibility = View.GONE
        binding.frameLayout.removeAllViews()
    }
    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
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
                            binding.isRepeatOrInstallment = true
                            binding.textRepeatOrInstallment = value
                        }
                    }
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
                    when (val value = data.getStringExtra("item")) {
                        TEXT_EMPTY -> return@registerForActivityResult
                        TEXT_NONE -> return@registerForActivityResult
                        else -> {
                            binding.isRepeatOrInstallment = true
                            binding.textRepeatOrInstallment = "$value $TEXT_INSTALLMENT"
                        }
                    }
                }
            }
        }
    }

    private fun showRepeatAndInstallmentAndCancelPopup() {
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
                R.id.select_cancel -> {
                    binding.isRepeatOrInstallment = false
                    true
                }
            }
            false
        }
        popup.menuInflater.inflate(R.menu.menu_select_repeat_and_installment_and_cancel, popup.menu)
        popup.show()
    }

    private fun showRepeatAndInstallmentPopup() {
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