package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.registerinput.fragment.AddAssetGroupDialog
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputAmountFragment
import com.example.accountbookuisampling.util.*
import com.google.android.material.textfield.TextInputEditText

class AddAssetInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetTextInputBinding
    private val TAG = this::class.java.simpleName
//    private var currentView = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        disableKeyboard()
//        currentView = intent.getIntExtra(TEXT_CURRENT_VIEW, -1)
        setFocusChangeEvent()
        setClickEvent()
//        binding.etGroup.requestFocus()
    }

    private fun setFocusChangeEvent() {
        binding.etGroup.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_GROUP)
            }
        }
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_AMOUNT)
            }
        }
    }

    fun changeInputAmount(value: String) {
        binding.etAmount.setText(value)
        binding.etAmount.clearFocus()
        hideCurrentInputView()
    }


        private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TEXT_GROUP_ID, 0)
            intent.putExtra(TEXT_NAME, "NAME")
            intent.putExtra(TEXT_AMOUNT, 100)
            intent.putExtra(TEXT_MEMO, "MEMO")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.layoutWrap.setOnClickListener {
            hideCurrentInputView()
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    override fun onBackPressed() {
        if (binding.frameLayout.visibility == View.VISIBLE) {
            hideCurrentInputView()
        } else {
            super.onBackPressed()
        }
    }

    private fun showInputFragment(flag: Int) {
        when (flag) {
            FLAG_GROUP -> {
                AddAssetGroupDialog().show(supportFragmentManager, TEXT_GROUP)
            }
            FLAG_AMOUNT -> {
                val transaction = supportFragmentManager.beginTransaction()
                val fragment =
                    RegisterInputAmountFragment(binding.etAmount.text.toString(), FLAG_AMOUNT)
                transaction.replace(binding.frameLayout.id, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                if (binding.frameLayout.visibility == View.GONE) binding.frameLayout.visibility =
                    View.VISIBLE
            }
        }
    }

    private fun disableKeyboard() {
        binding.etGroup.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun hideCurrentInputView() {
        binding.frameLayout.visibility = View.GONE
        binding.frameLayout.removeAllViews()
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }
}