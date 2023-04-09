package com.example.timerecord.fragment.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerecord.adapter.TodoHistoryAdapter
import com.example.timerecord.databinding.FragmentHomeTodoDetailBinding
import com.example.timerecord.util.Const
import com.example.timerecord.util.Util
import com.example.timerecord.viewmodel.TodoHistoryViewModel
import java.text.SimpleDateFormat

class HomeTodoDetailFragment : Fragment() {

    private var _binding: FragmentHomeTodoDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private var _list: ArrayList<TodoHistoryViewModel>? = null
    val list get() = _list!!

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
        _list = arrayListOf()
        list.addAll(Const.TEMP_TODO_HISTORY_HEAD_LIST)
        list.addAll(Util.fillTodoHistoryViewModelList(Const.TEMP_TODO_HISTORY_CONTENT_LIST))
        setClickEvent()
        setCalendar(list)
        setBackBtnEvent()
        setCurrentTime()
        setBindingData(list.filter { item -> item.targetDate == Util.getToday() }[0])
    }

    private fun setClickEvent() {
        binding.btnStartTime.setOnClickListener {
            list.forEachIndexed { index, item ->
                if (item.targetDate == binding.selectedDate) {
                    item.startTime =
                        SimpleDateFormat("HHmm").format(SimpleDateFormat("HH:mm:ss").parse(binding.currentTime).time)
                    list[index] = item
                    setBindingData(item)
                    binding.recyclerView.adapter?.notifyItemChanged(index)
                }
            }
        }

        binding.btnEndTime.setOnClickListener {
            list.forEachIndexed { index, item ->
                if (item.targetDate == binding.selectedDate) {
                    item.endTime =
                        SimpleDateFormat("HHmm").format(SimpleDateFormat("HH:mm:ss").parse(binding.currentTime).time)
                    list[index] = item
                    setBindingData(item)
                    binding.recyclerView.adapter?.notifyItemChanged(index)
                }
            }
        }
    }

    private fun setBindingData(item: TodoHistoryViewModel) {
        binding.selectedDate = item.targetDate
        (binding.recyclerView.adapter as TodoHistoryAdapter?)?.selectedDate = item.targetDate
        binding.selectedStartTime = item.startTime
        binding.selectedEndTime = item.endTime
    }

    private fun setCurrentTime() {
        binding.currentTime = Util.getCurrentTime()
        handler.postDelayed({
            setCurrentTime()
        }, Const.DELAY_ONE_SECONDS)
    }

    private fun setCalendar(input: List<TodoHistoryViewModel>) {
        handler.postDelayed({
            val layoutManager = GridLayoutManager(requireContext(), 7)
            layoutManager.orientation =
                LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.post {
                binding.recyclerView.adapter =
                    TodoHistoryAdapter(input, binding.selectedDate, ::changeDate)
                binding.showUI = true
            }
        }, Const.DELAY_SHOW_UI)
    }

    private fun changeDate(value: String, newIdx: Int) {
        val old = binding.selectedDate.toString()
        // old item refresh
        (binding.recyclerView.adapter as TodoHistoryAdapter).list.forEachIndexed { index, item ->
            if (item.targetDate == old) binding.recyclerView.adapter?.notifyItemChanged(index)
        }
        setBindingData(list[newIdx])
        // new item refresh
        binding.recyclerView.adapter?.notifyItemChanged(newIdx)
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

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:computeSelectedTime")
        fun computeSelectedTime(view: View, selectedDate: String) {

        }
    }
}