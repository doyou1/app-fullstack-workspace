package com.example.accountbookuisampling.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemCalendarContentBinding
import com.example.accountbookuisampling.databinding.RvItemCalendarHeadBinding
import com.example.accountbookuisampling.databinding.RvItemWeekBinding
import com.example.accountbookuisampling.viewmodel.CalendarItemViewModel
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.dataclass.History
import com.example.accountbookuisampling.dataclass.Week
import com.example.accountbookuisampling.util.DateUtil
import com.example.accountbookuisampling.util.TYPE_CALENDAR_CONTENT
import com.example.accountbookuisampling.util.TYPE_CALENDAR_HEAD
import com.example.accountbookuisampling.viewmodel.HistoryViewModel
import com.example.accountbookuisampling.viewmodel.WeekViewModel
import java.text.DecimalFormat

class WeekRVAdapter(private val list: ArrayList<Week>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName
    private var sequence = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeekViewHolder(RvItemWeekBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val week = _list[position]
        (holder as WeekViewHolder).bind(week)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class WeekViewHolder(private val binding: RvItemWeekBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(week: Week) {
            val model = formatToViewModel(week)
            binding.model = model
        }

        private fun formatToViewModel(week: Week): WeekViewModel {
            return WeekViewModel(week.period, week.income.toString(), week.consumption.toString(), week.result.toString())
        }
    }

}