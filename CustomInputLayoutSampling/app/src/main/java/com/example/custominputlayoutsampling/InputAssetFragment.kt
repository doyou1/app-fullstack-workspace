package com.example.custominputlayoutsampling

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputAssetBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputCategoryBinding


class InputAssetFragment(private val parentBinding: ActivityMainBinding) : Fragment() {

    private lateinit var binding: FragmentInputAssetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentBinding.etAsset.requestFocus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputAssetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), 3)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                binding.rvAsset.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), 3)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.rvAsset.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        val list = arrayListOf(
            "현금",
            "은행",
            "카드"
        )

        binding.rvAsset.adapter = AssetRVAdapter(list, parentBinding, parentFragmentManager)
    }

    companion object {

        @JvmStatic
        fun newInstance(binding: ActivityMainBinding) = InputAssetFragment(binding)
    }
}