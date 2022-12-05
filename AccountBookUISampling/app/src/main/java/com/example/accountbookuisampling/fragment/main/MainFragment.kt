package com.example.accountbookuisampling.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.accountbookuisampling.databinding.FragmentDayBinding

class MainFragment(
    private val yyyy: String,
    private val mm: String,
    private val dd: String,
    private val fragmentId: Int
) : Fragment() {

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDayBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        Log.e(TAG, "${fragmentId}")

        super.onResume()
    }
}
