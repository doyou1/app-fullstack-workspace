package com.example.vocabularynote.main.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.RvItemNoteBinding
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.util.Const
import com.example.vocabularynote.util.Const.TEXT_NOTE_ID

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
                    Const.TYPE_EDIT -> {
                        val bundle = Bundle()
                        bundle.putInt(TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_edit_to_edit_detail, bundle)
                    }
                    Const.TYPE_GAME -> {
                        val bundle = Bundle()
                        bundle.putInt(TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_game_to_game_detail, bundle)
                    }
                }
            }

        }

    }
}