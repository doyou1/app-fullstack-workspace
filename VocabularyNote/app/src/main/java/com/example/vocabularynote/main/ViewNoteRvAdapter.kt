package com.example.vocabularynote.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.databinding.RvItemViewNoteBinding
import com.example.vocabularynote.room.entity.NoteItem

class ViewNoteRvAdapter(private val _list: List<NoteItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemViewNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewNoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewNoteRvViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewNoteRvViewHolder(private val binding: RvItemViewNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.item = item
        }
    }
}