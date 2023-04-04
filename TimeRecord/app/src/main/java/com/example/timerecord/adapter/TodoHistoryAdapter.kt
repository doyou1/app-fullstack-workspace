package com.example.timerecord.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timerecord.databinding.RvItemTodoHistoryContentBinding
import com.example.timerecord.databinding.RvItemTodoHistoryHeadBinding
import com.example.timerecord.util.Const.TYPE_CALENDAR_CONTENT
import com.example.timerecord.util.Const.TYPE_CALENDAR_HEAD
import com.example.timerecord.util.Util
import com.example.timerecord.viewmodel.TodoHistoryViewModel

class TodoHistoryAdapter(private val _list: List<TodoHistoryViewModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvItemTodoHistoryHeadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
//                val layoutParams = binding.root.layoutParams
//                layoutParams.height = headHeight
//                binding.root.layoutParams = layoutParams
                TodoHistoryHeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT -> {
                val binding = RvItemTodoHistoryContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
//                val layoutParams = binding.root.layoutParams
//                layoutParams.height = contentHeight
//                binding.root.layoutParams = layoutParams
                TodoHistoryContentViewHolder(binding)
            }
            else -> {
                throw NotImplementedError()
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (item.viewType) {
            TYPE_CALENDAR_HEAD -> {
                (holder as TodoHistoryHeadViewHolder).bind(item)
            }
            TYPE_CALENDAR_CONTENT -> {
                (holder as TodoHistoryContentViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    inner class TodoHistoryHeadViewHolder(private val binding: RvItemTodoHistoryHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoHistoryViewModel) {
            binding.strDayOfWeek = item.strDayOfWeek
        }
    }

    inner class TodoHistoryContentViewHolder(private val binding: RvItemTodoHistoryContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoHistoryViewModel) {
            binding.viewModel = item
            binding.tvTargetDate.text =
                "${item.targetDate.substring(4, 6)}/${item.targetDate.substring(6, 8)}"
            binding.isToday = item.targetDate == Util.getToday()
        }
    }

}