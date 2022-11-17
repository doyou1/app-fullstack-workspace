package com.example.custominputlayoutsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInputLayout()
        disableKeyboard()
        setEditTextEvent()
        setClickEvent()
    }

    private fun setInputLayout() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
    }

    private fun disableKeyboard() {
        binding.etAsset.showSoftInputOnFocus = false
        binding.etCategory.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun setEditTextEvent() {
        binding.etAsset.setOnFocusChangeListener { view, hasFocus ->
            binding.isShowAsset = hasFocus
            if (hasFocus) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    binding.layoutInputContent.id,
                    InputAssetFragment.newInstance(binding)
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
        binding.etCategory.setOnFocusChangeListener { view, hasFocus ->
            binding.isShowCategory = hasFocus
            if (hasFocus) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    binding.layoutInputContent.id,
                    InputCategoryFragment.newInstance(binding)
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
        binding.etAmount.setOnFocusChangeListener { view, hasFocus ->
            binding.isShowAmount = hasFocus
            if (hasFocus) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    binding.layoutInputContent.id,
                    InputAmountFragment.newInstance(binding)
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        binding.layoutWrap.setOnClickListener {
            binding.isShowAsset = false
            binding.isShowCategory = false
            binding.isShowAmount = false
        }
    }

    private fun setClickEvent() {

    }
}