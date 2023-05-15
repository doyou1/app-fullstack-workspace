package com.example.vocabularynote.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vocabularynote.main.adapter.itemfragment.GameNoteVPFragment
import com.example.vocabularynote.room.viewmodel.GameNoteItemViewModel

class GameNoteVPAdapter(private val _list: List<GameNoteItemViewModel>, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    private val list: MutableList<GameNoteItemViewModel> = _list.toMutableList()
    override fun getItemCount(): Int = list.size
    override fun createFragment(position: Int): Fragment = GameNoteVPFragment(list[position], position)
}