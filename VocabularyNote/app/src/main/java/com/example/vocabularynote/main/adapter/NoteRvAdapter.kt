package com.example.vocabularynote.main.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.BaseApplication
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.RvItemNoteBinding
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.util.AppMsgUtil
import com.example.vocabularynote.util.Const
import com.example.vocabularynote.util.Const.TEXT_CANCEL
import com.example.vocabularynote.util.Const.TEXT_DELETE
import com.example.vocabularynote.util.Const.TEXT_EDIT
import com.example.vocabularynote.util.Const.TEXT_NO
import com.example.vocabularynote.util.Const.TEXT_NOTE_ID
import com.example.vocabularynote.util.Const.TEXT_YES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteRvAdapter(private val _list: List<Note>, private val parentViewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
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
            setClickEvent(item)
        }

        private fun setClickEvent(item: Note) {
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
                when (parentViewType) {
                    Const.TYPE_EDIT -> {
                        popup.menu.add(TEXT_EDIT)
                        popup.menu.add(TEXT_DELETE)
                        popup.menu.add(TEXT_CANCEL)
                    }
                    Const.TYPE_GAME -> {
                        popup.menu.add(TEXT_EDIT)
                        popup.menu.add(TEXT_CANCEL)
                    }
                }
                popup.setOnMenuItemClickListener {
                    when (it.title) {
                        TEXT_EDIT -> {
                            val bundle = Bundle()
                            bundle.putLong(TEXT_NOTE_ID, item.id)
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_edit_to_add, bundle)
                        }
                        TEXT_DELETE -> {
                            showDeletePrompt(item)
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

        private fun showDeletePrompt(item: Note) {
            val builder = AlertDialog.Builder(context)
            val alert = builder
                .setMessage("Do you want to DELETE \"${item.title}\" Note? \n※If deleted, it cannot be reversed.")
                .setCancelable(true)
                .setPositiveButton(TEXT_YES) { _, _ ->
                    Log.e(TAG, "positive button")
                    GlobalScope.launch(Dispatchers.IO) {
                        ((context as Activity).application as BaseApplication).noteDao.deleteNote(
                            item
                        )
                        GlobalScope.launch(Dispatchers.Main) {
                            AppMsgUtil.showMsg(Const.TEXT_DELETE_NOTE_SUCCESS, (context as Activity))
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_refresh_edit)
                        }
                    }

                }
                .setNegativeButton(TEXT_NO) { _, _ ->
//                    Log.e(TAG, "negative button")
                }.create()
            alert.setTitle("Check to delete note")
            alert.show()
        }

    }
}