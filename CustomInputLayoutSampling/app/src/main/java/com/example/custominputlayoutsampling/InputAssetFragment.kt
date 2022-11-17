package com.example.custominputlayoutsampling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputAssetBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputCategoryBinding


class InputAssetFragment(private val parentBinding: ActivityMainBinding) : Fragment() {

    private lateinit var binding: FragmentInputAssetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnAsset.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(parentBinding.layoutInputContent.id, InputCategoryFragment.newInstance(parentBinding))
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(binding: ActivityMainBinding) = InputAssetFragment(binding)
    }
}