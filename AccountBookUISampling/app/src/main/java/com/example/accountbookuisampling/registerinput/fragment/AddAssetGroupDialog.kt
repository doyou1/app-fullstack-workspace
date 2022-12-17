package com.example.accountbookuisampling.registerinput.fragment

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.databinding.DialogAddAssetGroupBinding
import com.example.accountbookuisampling.registerinput.activity.AddAssetInputActivity
import com.example.accountbookuisampling.registerinput.adapter.AddAssetGroupAdapter
import com.example.accountbookuisampling.util.INIT_ASSET_LIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAssetGroupDialog : DialogFragment() {

    private lateinit var binding: DialogAddAssetGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddAssetGroupBinding.inflate(layoutInflater)
//        return super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setData()
        setRecyclerView()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val list = (requireActivity().application as BaseApplication).assetDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
//                (requireActivity() as MainActivity).updateSummary(1000, 2000, 1000 - 2000)
            }
        }
    }

    private fun setRecyclerView() {
        setLayoutManager()
        val list = INIT_ASSET_LIST
        // 追加アイテム削除
        list.removeLast()
        binding.recyclerView.adapter = AddAssetGroupAdapter(list, this)
    }
    private fun setLayoutManager() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                GridLayoutManager(context, 2)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager(context)
            }
            else -> throw NotImplementedError()
        }

        binding.recyclerView.layoutManager = layoutManager
    }

    fun changeGroup(value: String) {
        (requireActivity() as AddAssetInputActivity).changeGroup(value)
        dismiss()
    }
}