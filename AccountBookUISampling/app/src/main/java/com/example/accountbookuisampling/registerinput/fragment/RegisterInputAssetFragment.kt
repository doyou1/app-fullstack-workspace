package com.example.accountbookuisampling.registerinput.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.accountbookuisampling.databinding.FragmentRegisterInputAssetBinding

class RegisterInputAssetFragment : Fragment() {
    private lateinit var binding: FragmentRegisterInputAssetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputAssetBinding.inflate(inflater, container, false)
        return binding.root
    }
}