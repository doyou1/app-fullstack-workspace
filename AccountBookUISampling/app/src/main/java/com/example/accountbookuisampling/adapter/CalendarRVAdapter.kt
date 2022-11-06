package com.example.accountbookuisampling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemCalendarBinding
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.viewmodel.CalendarItemViewModel

class CalendarRVAdapter(private val list: ArrayList<CalendarItem>) :
    RecyclerView.Adapter<CalendarRVAdapter.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RvItemCalendarBinding =
            RvItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarRVAdapter.ViewHolder, position: Int) {
        val calendarItem = _list[position]
        holder.bind(calendarItem)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ViewHolder(val binding: RvItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarItem: CalendarItem) {
            val model = formatToViewModel(calendarItem)
            binding.model = model
        }

        private fun formatToViewModel(calendarItem: CalendarItem) : CalendarItemViewModel {

            return CalendarItemViewModel("","","","","","","")
        }
    }

}