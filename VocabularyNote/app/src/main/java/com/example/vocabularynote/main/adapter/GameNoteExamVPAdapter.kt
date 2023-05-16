package com.example.vocabularynote.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vocabularynote.main.adapter.itemfragment.GameNoteVPFragment
import com.example.vocabularynote.room.viewmodel.GameNoteItemFlipViewModel

class GameNoteExamVPAdapter(private val _list: List<GameNoteItemFlipViewModel>, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    private val list: MutableList<GameNoteItemFlipViewModel> = _list.toMutableList()
    override fun getItemCount(): Int = list.size
    override fun createFragment(position: Int): Fragment = GameNoteVPFragment(list[position])
}