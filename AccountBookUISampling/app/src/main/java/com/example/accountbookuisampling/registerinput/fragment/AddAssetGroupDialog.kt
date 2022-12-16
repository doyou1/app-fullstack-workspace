package com.example.accountbookuisampling.registerinput.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.accountbookuisampling.databinding.DialogAddAssetGroupBinding

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        return dialog
    }
}