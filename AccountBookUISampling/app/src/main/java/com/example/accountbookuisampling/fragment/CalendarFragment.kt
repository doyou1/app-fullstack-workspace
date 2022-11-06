package com.example.accountbookuisampling.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.databinding.FragmentCalendarBinding
import com.example.accountbookuisampling.databinding.FragmentDayBinding

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView()
    }

    private fun setRecyclerView() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = CalendarFragment()
    }
}