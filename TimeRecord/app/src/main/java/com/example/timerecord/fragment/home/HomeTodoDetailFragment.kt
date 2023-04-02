package com.example.timerecord.fragment.home

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerecord.adapter.TodoHistoryAdapter
import com.example.timerecord.databinding.FragmentHomeTodoDetailBinding
import com.example.timerecord.entity.Todo
import com.example.timerecord.entity.TodoHistory
import com.example.timerecord.util.Const
import com.example.timerecord.util.Const.MORE_DELAY_SHOW_UI
import com.example.timerecord.util.Const.TEMP_TODO_HISTORY_CONTENT_LIST
import com.example.timerecord.util.Const.TEMP_TODO_HISTORY_HEAD_LIST
import com.example.timerecord.util.RoomDBHelper
import com.example.timerecord.util.Util
import com.example.timerecord.viewmodel.TodoHistoryViewModel
import com.google.gson.Gson

class HomeTodoDetailFragment : Fragment() {

    private var _binding: FragmentHomeTodoDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            arguments?.getString("item")?.let {
//                val item = Gson().fromJson(it, Todo::class.java)
//                val list = RoomDBHelper.getTodoHistory(item)
//                Log.e(TAG, "item: $item")
//            }
        setCalendar(listOf())
        setBackBtnEvent()


    }

    private fun setCalendar(input: List<TodoHistory>) {
        handler.postDelayed({
            val layoutManager = GridLayoutManager(requireContext(), 7)
            layoutManager.orientation =
                LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = layoutManager
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.post {
                val list = arrayListOf<TodoHistoryViewModel>()
                list.addAll(TEMP_TODO_HISTORY_HEAD_LIST)
                list.addAll(Util.fillTodoHistoryViewModelList(TEMP_TODO_HISTORY_CONTENT_LIST))
                binding.recyclerView.adapter = TodoHistoryAdapter(list)
            }
            binding.showUI = true
        }, Const.DELAY_SHOW_UI)
    }

    private fun setBackBtnEvent() {
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}