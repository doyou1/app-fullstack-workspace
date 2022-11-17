package com.example.custominputlayoutsampling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputCategoryBinding


class InputCategoryFragment(private val parentBinding: ActivityMainBinding) : Fragment() {

    private lateinit var binding: FragmentInputCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnCategory.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(parentBinding.layoutInputContent.id, InputAmountFragment.newInstance(parentBinding))
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(binding: ActivityMainBinding) = InputCategoryFragment(binding)
    }
}