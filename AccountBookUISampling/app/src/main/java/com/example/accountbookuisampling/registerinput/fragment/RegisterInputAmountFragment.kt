package com.example.accountbookuisampling.registerinput.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.accountbookuisampling.databinding.FragmentRegisterInputAmountBinding

class RegisterInputAmountFragment: Fragment() {

    private lateinit var binding: FragmentRegisterInputAmountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputAmountBinding.inflate(inflater, container, false)
        return binding.root
    }
}