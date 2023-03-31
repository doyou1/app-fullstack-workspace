package com.example.timerecord.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timerecord.databinding.RvItemTodoBinding
import com.example.timerecord.entity.Todo

class TodoAdapter(private val _list: List<Todo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TodoViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TodoViewHolder(private val binding: RvItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Todo) {
            binding.viewModel = item

            binding.root.setOnClickListener {
                Log.e(TAG, "item: $item")
            }
        }
    }

}