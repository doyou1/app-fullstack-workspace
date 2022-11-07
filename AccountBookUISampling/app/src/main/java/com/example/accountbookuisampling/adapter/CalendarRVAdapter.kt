package com.example.accountbookuisampling.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemCalendarContentBinding
import com.example.accountbookuisampling.databinding.RvItemCalendarHeadBinding
import com.example.accountbookuisampling.viewmodel.CalendarItemViewModel
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.util.TYPE_CALENDAR_CONTENT
import com.example.accountbookuisampling.util.TYPE_CALENDAR_HEAD

class CalendarRVAdapter(private val list: ArrayList<CalendarItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName
    private var sequence = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val headHeight = parent.measuredHeight / 8
        val contentHeight = (parent.measuredHeight - headHeight) / 5

        when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvItemCalendarHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val layoutParams =  binding.root.layoutParams
                layoutParams.height = headHeight
                binding.root.layoutParams = layoutParams
                return HeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT -> {
                val binding = RvItemCalendarContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val layoutParams =  binding.root.layoutParams
                layoutParams.height = contentHeight
                binding.root.layoutParams = layoutParams
                return ContentViewHolder(binding)
            }
            else -> {
                throw NotImplementedError()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val calendarItem = _list[position]

        when (calendarItem.viewType) {
            TYPE_CALENDAR_HEAD -> {
                (holder as HeadViewHolder).bind(calendarItem)
            }
            TYPE_CALENDAR_CONTENT -> {
                (holder as ContentViewHolder).bind(calendarItem)
            }
            else -> throw NotImplementedError()
        }

    }

    override fun getItemCount(): Int {
        return _list.size
    }

    override fun getItemViewType(position: Int): Int {
        return _list[position].viewType
    }

    inner class HeadViewHolder(private val binding: RvItemCalendarHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarItem: CalendarItem)
        {
            if(calendarItem.viewType == TYPE_CALENDAR_HEAD) {
                binding.strDayOfWeek = when(sequence++) {
                    0 -> "일"
                    1 -> "월"
                    2 -> "화"
                    3 -> "수"
                    4 -> "목"
                    5 -> "금"
                    6 -> "토"
                    else -> ""
                }
            } else {
                binding.strDayOfWeek = ""
            }
        }
    }

    inner class ContentViewHolder(private val binding: RvItemCalendarContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarItem: CalendarItem) {
            val model = formatToViewModel(calendarItem)
            binding.model = model
        }

        private fun formatToViewModel(calendarItem: CalendarItem): CalendarItemViewModel {
            val day = calendarItem.date
            return CalendarItemViewModel(day, calendarItem.consumption.toString(), calendarItem.income.toString(), calendarItem.result.toString())
        }
    }

}