package com.example.vocabularynote.main.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.databinding.RvItemEditNoteBinding
import com.example.vocabularynote.entity.NoteItem
import com.google.android.material.textfield.TextInputEditText

class EditNoteRvAdapter(private val _list: List<NoteItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<NoteItem> = _list.toMutableList()
    private var parentContext: Context? = null
    private var addCount = 1
    private val additionList: MutableList<NoteItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemEditNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return EditNoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list.size <= position) {
            if (addCount - additionList.size >= 1) {
                val item = NoteItem()
                additionList.add(item)
                (holder as EditNoteRvViewHolder).bind(item)
            } else {
                val item = additionList[position - list.size]
                (holder as EditNoteRvViewHolder).bind(item)
            }
        } else {
            val item = list[position]
            (holder as EditNoteRvViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = list.size + addCount

    fun addEditItem(): Int {
        addCount++
        return list.size + addCount
    }

    fun print() {
        val printList = arrayListOf<NoteItem>()
        printList.addAll(list)
        printList.addAll(additionList)
        Log.e("tag", printList.toString())
    }

    fun save(): List<NoteItem> {
        val result = arrayListOf<NoteItem>()
        list.forEach { item ->
            if (!(item.key == "" || item.value == "")) result.add(item)
        }
        additionList.forEach { item ->
            if (!(item.key == "" || item.value == "")) result.add(item)
        }
        return result.toList()
    }

    inner class EditNoteRvViewHolder(private val binding: RvItemEditNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.item = item
            binding.etKey.addTextChangedListener(
                onTextChanged = { it: CharSequence?, _: Int, _: Int, _: Int ->
                    itemCount
                    val item = if (list.size <= adapterPosition) {
                        additionList[adapterPosition - list.size]
                    } else {
                        list[adapterPosition]
                    }
                    item.key = it.toString()
                    if (list.size <= adapterPosition) {
                        additionList[adapterPosition - list.size] = item
                    } else {
                        list[adapterPosition] = item
                    }
                }
            )
            binding.etValue.addTextChangedListener(
                onTextChanged = { it: CharSequence?, _: Int, _: Int, _: Int ->
                    val item = if (list.size <= adapterPosition) {
                        additionList[adapterPosition - list.size]
                    } else {
                        list[adapterPosition]
                    }
                    item.value = it.toString()
                    if (list.size <= adapterPosition) {
                        additionList[adapterPosition - list.size] = item
                    } else {
                        list[adapterPosition] = item
                    }
                }
            )

            binding.root.setOnClickListener {
                if (it !is TextInputEditText) {
                    hideKeyboard()
                }
            }
        }

        private fun hideKeyboard() {
            val im =
                parentContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow((parentContext as Activity?)?.currentFocus?.windowToken, 0)
            (parentContext as Activity?)?.currentFocus?.clearFocus()
        }
    }
}