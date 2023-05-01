package com.example.vocabularynote.main.adapter

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.RvItemGameNoteBinding
import com.example.vocabularynote.entity.NoteItem

class GameNoteRvAdapter(_list: List<NoteItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<NoteItem> = _list.toMutableList()
    private var parentContext: Context? = null
    private val showKeyArr = arrayListOf<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemGameNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return GameNoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if(showKeyArr.size >= position) {
            showKeyArr.add(true)
        }
        (holder as GameNoteRvViewHolder).bind(item)
    }

    override fun getItemCount(): Int = list.size

    inner class GameNoteRvViewHolder(private val binding: RvItemGameNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.item = item
            binding.showKey = showKeyArr[adapterPosition]
            setClickEvent()
        }

        private fun setClickEvent() {
            binding.layoutWrap.setOnClickListener {
                // Now we will set the front animation
                val frontAnimation = AnimatorInflater.loadAnimator(
                    parentContext,
                    R.animator.game_card_front_animator
                ) as AnimatorSet
                val backAnimation = AnimatorInflater.loadAnimator(
                    parentContext,
                    R.animator.game_card_back_animator
                ) as AnimatorSet
                val currentShowKey = showKeyArr[adapterPosition]
                if (currentShowKey) {
                    frontAnimation.setTarget(binding.layoutKey)
                    backAnimation.setTarget(binding.layoutValue)
                    frontAnimation.start()
                    backAnimation.start()
                } else {
                    frontAnimation.setTarget(binding.layoutValue)
                    backAnimation.setTarget(binding.layoutKey)
                    frontAnimation.start()
                    backAnimation.start()
                }
                showKeyArr[adapterPosition] = !showKeyArr[adapterPosition]
                binding.showKey = showKeyArr[adapterPosition]

            }
        }
    }
}