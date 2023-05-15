package com.example.vocabularynote.main.adapter

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.api.TranslationApiHelper
import com.example.vocabularynote.databinding.RvItemEditNoteBinding
import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.room.viewmodel.NoteItemViewModel
import com.example.vocabularynote.util.Const
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditNoteRvAdapter(
    _list: List<NoteItemViewModel>,
    private val noteId: Long,
    _nextId: Long,
    private val useTranslation: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list: MutableList<NoteItemViewModel> = _list.toMutableList()
    private var parentContext: Context? = null
    private var addCount = 1
    private var nextId = _nextId
    private val additionList: MutableList<NoteItemViewModel> = mutableListOf()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemEditNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return EditNoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list.size <= position) {
            if (addCount - additionList.size >= 1) {
                val item = NoteItemViewModel(id = nextId, noteId = noteId)
                additionList.add(item)
                (holder as EditNoteRvViewHolder).bind(item)
                nextId++
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

    fun addAllEditItem(list: List<NoteItemViewModel>): Int {
        for (_item in list) {
            val item = NoteItemViewModel(
                id = nextId,
                noteId = noteId,
                key = _item.key,
                value = _item.value
            )
            additionList.add(item)
            nextId++
        }
        addCount += list.size
        return list.size + addCount
    }

    fun print() {
        val result = arrayListOf<NoteItem>()
        list.forEach { item ->
            if (item.key != "") {
                if (item.value != "") {
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value
                        )
                    )
                } else if (item.translatedText != "") {  // item.value == "" is always true
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.translatedText
                        )
                    )
                }
            }
        }
        additionList.forEach { item ->
            if (item.key != "") {
                if (item.value != "") {
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value
                        )
                    )
                } else if (item.translatedText != "") {  // item.value == "" is always true
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.translatedText
                        )
                    )
                }
            }
        }
        Log.e(TAG, "print result: $result")
    }

    fun getResult(): List<NoteItem> {
        val result = arrayListOf<NoteItem>()
        list.forEach { item ->
            if (item.key != "") {
                if (item.value != "") {
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value
                        )
                    )
                } else if (item.translatedText != "") {  // item.value == "" is always true
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.translatedText
                        )
                    )
                }
            }
        }
        additionList.forEach { item ->
            if (item.key != "") {
                if (item.value != "") {
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value
                        )
                    )
                } else if (item.translatedText != "") {  // item.value == "" is always true
                    result.add(
                        NoteItem(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.translatedText
                        )
                    )
                }
            }
        }
        return result.toList()
    }

    inner class EditNoteRvViewHolder(private val binding: RvItemEditNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var prevTextChangedTime: Long = -1L

        fun bind(item: NoteItemViewModel) {
            binding.item = item
            setClickEvent()
            setTextChangeEvent()
            aboutKeyboard()
        }

        private fun setClickEvent() {
            binding.btnRemove.setOnClickListener {
                if (list.size <= adapterPosition) {
                    additionList.removeAt(adapterPosition - list.size)
                    addCount--
                } else {
                    list.removeAt(adapterPosition)
                }
                notifyItemRemoved(adapterPosition)
            }
        }

        private fun setTextChangeEvent() {
            binding.etKey.addTextChangedListener(
                onTextChanged = { it: CharSequence?, _: Int, _: Int, _: Int ->
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
                },
                afterTextChanged = {
                    if (useTranslation && isNecessaryHint()) {
                        binding.isExecute = true
                        val currentTextChangedTime = System.currentTimeMillis()
                        if (currentTextChangedTime - prevTextChangedTime < Const.DELAY_EXECUTE_TRANSLATION) {
                            handler.removeCallbacksAndMessages(null)
                        }
                        prevTextChangedTime = currentTextChangedTime
                        handler.postDelayed({
                            processTranslation(it.toString())
                        }, Const.DELAY_EXECUTE_TRANSLATION)
                    }
                })
            binding.etValue.addTextChangedListener(onTextChanged = { it: CharSequence?, _: Int, _: Int, _: Int ->
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
            })
        }

        private fun aboutKeyboard() {
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

        private fun isNecessaryHint(): Boolean {
            val key = binding.etValue.text?.toString() ?: return false
            val value = binding.etValue.text?.toString()
            return key.isNotEmpty() && value.isNullOrEmpty()
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun processTranslation(key: String) {
            if (useTranslation && isNecessaryHint()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val translatedText = TranslationApiHelper.getValue(
                        source = "en", target = "ko", key = key
                    )?.message?.result?.get("translatedText")
                    GlobalScope.launch(Dispatchers.Main) {
                        if (translatedText != null) {
                            val item = if (list.size <= adapterPosition) {
                                additionList[adapterPosition - list.size]
                            } else {
                                list[adapterPosition]
                            }
                            item.translatedText = translatedText
                            binding.translatedText = translatedText
                            if (list.size <= adapterPosition) {
                                additionList[adapterPosition - list.size] = item
                            } else {
                                list[adapterPosition] = item
                            }
                        }
                    }
                }
            }
            prevTextChangedTime = -1
            binding.isExecute = false
        }
    }
}