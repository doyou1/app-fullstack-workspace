package com.example.vocabularynote.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.databinding.RvItemMainEditBinding
import com.example.vocabularynote.entity.Note

class MainEditRvAdapter(val _list: List<Note>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding =
            RvItemMainEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainEditRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MainEditRvViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MainEditRvViewHolder(private val binding: RvItemMainEditBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.item = item
        }

    }
}