package com.example.vocabularynote.main.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.RvItemGameNoteBinding
import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.util.CarouselLayoutManager

class GameNoteRvAdapter(_list: List<NoteItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list: MutableList<NoteItem> = _list.toMutableList()
    private val showKeys: MutableList<Boolean> = MutableList(list.size) { _ -> true }
    private var context: Context? = null
    private var recyclerView: RecyclerView? = null

    //    private val showKeyArr = arrayListOf<Boolean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemGameNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return GameNoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[holder.adapterPosition]
        (holder as GameNoteRvViewHolder).bind(item, position)
        Log.e(
            "position log onBindViewHolder",
            "key: ${item.key},position: $position, holder.oldPosition: ${holder.oldPosition}, holder.adapterPosition: ${holder.adapterPosition}, holder.layoutPosition: ${holder.layoutPosition}"
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class GameNoteRvViewHolder(private val binding: RvItemGameNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Now we will set the front animation
        private val frontAnimation = AnimatorInflater.loadAnimator(
            context,
            R.animator.game_card_front_animator
        ) as AnimatorSet
        private val backAnimation = AnimatorInflater.loadAnimator(
            context,
            R.animator.game_card_back_animator
        ) as AnimatorSet

        fun bind(item: NoteItem, position: Int) {
            binding.item = item
            binding.showKey = showKeys[adapterPosition]
            setClickEvent(item.key, position)
        }

        private fun getWrapView(): View {
            return binding.layoutWrap.rootView
        }

        private fun setClickEvent(key: String, position: Int) {
            getWrapView().setOnClickListener {
                Log.e(
                    "position log setClick",
                    "key: $key, position: $position, oldPosition: ${oldPosition}, adapterPosition: ${adapterPosition}, layoutPosition: ${layoutPosition}"
                )

                (recyclerView?.layoutManager as CarouselLayoutManager).isScrollEnabled = false
                if (showKeys[adapterPosition]) {
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
                showKeys[layoutPosition] = !showKeys[adapterPosition]
                binding.showKey = showKeys[adapterPosition]
                backAnimation.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        (recyclerView?.layoutManager as CarouselLayoutManager).isScrollEnabled =
                            true
                    }
                })
            }
        }
    }
}