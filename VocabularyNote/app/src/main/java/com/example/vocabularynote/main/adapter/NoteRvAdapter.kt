package com.example.vocabularynote.main.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.RvItemNoteBinding
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.util.Const
import com.example.vocabularynote.util.Const.TEXT_CANCEL
import com.example.vocabularynote.util.Const.TEXT_EDIT
import com.example.vocabularynote.util.Const.TEXT_NOTE_ID

class NoteRvAdapter(private val _list: List<Note>, private val parentViewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    private var _context: Context? = null
    private val context get() = _context!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _context = parent.context
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
                        bundle.putLong(TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_edit_to_edit_detail, bundle)
                    }
                    Const.TYPE_GAME -> {
                        val bundle = Bundle()
                        bundle.putLong(TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_game_to_game_detail, bundle)
                    }
                }
            }

            binding.btnEdit.setOnClickListener {
                val popup = PopupMenu(context, binding.btnEdit)
                popup.menu.add(TEXT_EDIT)
                popup.menu.add(TEXT_CANCEL)
                popup.setOnMenuItemClickListener {
                    when (it.title) {
                        TEXT_EDIT -> {
                            val bundle = Bundle()
                            bundle.putLong(TEXT_NOTE_ID, item.id)
                            Navigation.findNavController(binding.root).navigate(R.id.action_edit_to_add, bundle)
                        }
                        TEXT_CANCEL -> {
                            popup.dismiss()
                        }
                    }
                    false
                }
                popup.show()
            }

        }

    }
}