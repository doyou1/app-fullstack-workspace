package com.example.accountbookuisampling.registerinput.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.accountbookuisampling.databinding.FragmentRegisterInputCategoryBinding
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment

class RegisterInputCategoryFragment: Fragment() {

    private lateinit var binding: FragmentRegisterInputCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputCategoryBinding.inflate(inflater, container, false)
//        (requireParentFragment() as BaseRegisterFragment).changeInChild()
        return binding.root
    }
}