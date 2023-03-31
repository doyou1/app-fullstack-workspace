package com.example.timerecord.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerecord.Const.DELAY_SHOW_UI
import com.example.timerecord.Const.TEMP_TODO_LIST
import com.example.timerecord.adapter.TodoAdapter
import com.example.timerecord.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            initRecyclerView()
            binding.showUI = true
        }, DELAY_SHOW_UI)
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = TodoAdapter(TEMP_TODO_LIST)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
//        binding.showUI = false
    }

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance!!
        }
    }
}