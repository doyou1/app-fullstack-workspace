package com.example.custominputlayoutsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableKeyboard()
        setEditTextEvent()
        setClickEvent()
    }

    private fun disableKeyboard() {
        binding.etAsset.showSoftInputOnFocus = false
        binding.etCategory.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun setEditTextEvent() {
        binding.etAsset.setOnFocusChangeListener { view, hasFocus ->
            clearFlag()
            binding.isShowAsset = true
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
            clearFlag()
            binding.isShowCategory = true
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
            clearFlag()
            binding.isShowAmount = true
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
    }

    fun clearFlag() {
        binding.isShowAsset = false
        binding.isShowCategory = false
        binding.isShowAmount = false
    }

    private fun setClickEvent() {
        binding.layoutWrap.setOnClickListener {
            // 순서에 따라 동작이 달라짐
            /**
             *  currentFocus?.clearFocus()
             *  가 앞에 있으면 View.GONE 활성화
             *  가 뒤에 있으면 View.GONE 비활성화
             *
             *  왜 일까?
             */
            currentFocus?.clearFocus()
            binding.isShowAsset = false
            binding.isShowCategory = false
            binding.isShowAmount = false
        }
    }
}