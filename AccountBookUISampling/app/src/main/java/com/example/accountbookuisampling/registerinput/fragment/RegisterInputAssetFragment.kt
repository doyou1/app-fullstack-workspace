package com.example.accountbookuisampling.registerinput.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.databinding.FragmentRegisterInputAssetBinding
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment
import com.example.accountbookuisampling.registerinput.activity.AddTextInputActivity
import com.example.accountbookuisampling.registerinput.adapter.InputAssetAdapter
import com.example.accountbookuisampling.util.*

class RegisterInputAssetFragment : BaseRegisterInputFragment() {

    private lateinit var binding: FragmentRegisterInputAssetBinding
    private val TAG = this::class.java.simpleName
    private val addTextInputActivityResultLauncher = getAddTextInputActivityResultLauncher()
    private val assetList = arrayListOf(
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
        binding = FragmentRegisterInputAssetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
        setView()
    }

    private fun setClickEvent() {

        binding.btnViewType.setOnClickListener {
            Log.e(TAG, "btnViewType")
        }

        binding.btnEdit.setOnClickListener {
            Log.e(TAG, "btnEdit")
        }

        binding.btnClose.setOnClickListener {
            (requireParentFragment() as BaseRegisterFragment).closeInputLayout()
        }


    }

    private fun setView() {
        binding.tvTitle.text = TEXT_ASSET

        setLayoutManager()
        binding.recyclerView.adapter =
            InputAssetAdapter(assetList, this)
    }

    private fun setLayoutManager() {
        // set recyclerview layout like grid view
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun openAddTextInputActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            AddTextInputActivity::class.java
        )
        intent.putExtra(TEXT_FLAG, FLAG_ASSET)
        addTextInputActivityResultLauncher.launch(
            intent
        )
    }

    override fun changeInputText(value: String) {
        (requireParentFragment() as BaseRegisterFragment).changeInputTextFromChild(
            value, FLAG_ASSET
        )
    }

    private fun getAddTextInputActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    assetList.add(assetList.size - 1, data.getStringExtra(TEXT_ITEM)!!)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }

            }
        }
    }

}