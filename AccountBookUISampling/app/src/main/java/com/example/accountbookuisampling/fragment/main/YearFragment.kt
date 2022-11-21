package com.example.accountbookuisampling.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.accountbookuisampling.databinding.FragmentYearBinding

class YearFragment : Fragment() {

    private lateinit var binding: FragmentYearBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentYearBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private var instance: YearFragment? = null
        @JvmStatic
        fun getInstance() : YearFragment {
            if(instance == null) {
                instance = YearFragment()
                return instance as YearFragment
            }

            return instance as YearFragment
        }
    }
}