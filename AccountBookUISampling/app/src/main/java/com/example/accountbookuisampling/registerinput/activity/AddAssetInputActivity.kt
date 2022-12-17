package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputAmountFragment
import com.example.accountbookuisampling.util.*
import com.google.android.material.textfield.TextInputEditText

class AddAssetInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetTextInputBinding
    private val TAG = this::class.java.simpleName
    private val addAssetGroupActivityResultLauncher = getAddAssetGroupActivityResultLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        disableKeyboard()
        setFocusChangeEvent()
        setClickEvent()
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

    fun changeGroup(value: String) {
        binding.etGroup.setText(value)
        binding.etGroup.clearFocus()
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
                openAddAssetGroupActivityResultLauncher()
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


    private fun openAddAssetGroupActivityResultLauncher() {
        val intent = Intent(this, AddAssetGroupActivity::class.java)
        addAssetGroupActivityResultLauncher.launch(
            intent
        )
    }

    private fun getAddAssetGroupActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.e(TAG, "result ok")
                result.data?.let { data ->
                    val value = data.getStringExtra(TEXT_GROUP)
                    Log.e(TAG, "value: $value")
                    binding.etGroup.setText(value)

                }
            }
            binding.etGroup.clearFocus()
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