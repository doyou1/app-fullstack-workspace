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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.databinding.FragmentRegisterInputAssetBinding
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment
import com.example.accountbookuisampling.registerinput.activity.AddAssetInputActivity
import com.example.accountbookuisampling.registerinput.adapter.InputAssetAdapter
import com.example.accountbookuisampling.room.entities.Asset
import com.example.accountbookuisampling.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterInputAssetFragment : BaseRegisterInputFragment() {

    private lateinit var binding: FragmentRegisterInputAssetBinding
    private val TAG = this::class.java.simpleName
    private val addTextInputActivityResultLauncher = getAddTextInputActivityResultLauncher()
    private val _list = ArrayList<Asset>()

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
        setData()
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

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val list = (requireActivity().application as BaseApplication).assetDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
//                (requireActivity() as MainActivity).updateSummary(1000, 2000, 1000 - 2000)
            }
        }
    }

    private fun setView() {
        binding.tvTitle.text = TEXT_ASSET

        if (_list.isEmpty()) {
            _list.addAll(INIT_ASSET_LIST)
        }

        setLayoutManager()
        binding.recyclerView.adapter =
            InputAssetAdapter(_list, this)
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
            AddAssetInputActivity::class.java
        )
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
                    val groupId = data.getIntExtra(TEXT_GROUP_ID, -1)
                    val name = data.getStringExtra(TEXT_NAME)
                    val amount = data.getIntExtra(TEXT_AMOUNT, -1)
                    val memo = data.getStringExtra(TEXT_MEMO)

                    if (!(groupId == -1 || name.isNullOrEmpty() || amount == -1)) {
//                        val level = 0
//                        val item = Asset(0, groupId, level, name, amount, memo)
//                        (requireActivity().application as BaseApplication).assetDao.insert(item)
//                        _list.add(_list.size - 1, item)
//                        binding.recyclerView.adapter?.notifyDataSetChanged()
                    }

                }
            }
        }
    }

}