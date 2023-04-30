package com.example.vocabularynote.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.R
import com.example.vocabularynote.Temp
import com.example.vocabularynote.databinding.RvItemNoteBinding
import com.example.vocabularynote.entity.Note

class NoteRvAdapter(private val _list: List<Note>, private val parentViewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding =
            RvItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteRvViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NoteRvViewHolder(private val binding: RvItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.item = item
            binding.layoutWrap.setOnClickListener {
                when (parentViewType) {
                    Temp.TYPE_EDIT -> {
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_edit_to_edit_detail)
                    }
                    Temp.TYPE_GAME -> {
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_game_to_game_detail)
                    }
                }
            }

        }

    }
}