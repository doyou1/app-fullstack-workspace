package com.example.accountbookuisampling.adapter

import android.graphics.Color
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
import java.text.DecimalFormat

class CalendarRVAdapter(private val list: ArrayList<CalendarItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val headHeight = parent.measuredHeight / 20
        val contentHeight = (parent.measuredHeight - headHeight) / 5

        when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvItemCalendarHeadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.root.layoutParams.height = headHeight
                return HeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT -> {
                val binding = RvItemCalendarContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.root.layoutParams.height = contentHeight
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

        fun bind(calendarItem: CalendarItem) {
            when (calendarItem.dayOfWeekSequence) {
                0 -> {
                    binding.strDayOfWeek = "일"
                    binding.tvDayOfWeek.setTextColor(Color.RED)
                }
                1 -> {
                    binding.strDayOfWeek = "월"
                    binding.tvDayOfWeek.setTextColor(Color.GRAY)
                }
                2 -> {
                    binding.strDayOfWeek = "화"
                    binding.tvDayOfWeek.setTextColor(Color.GRAY)
                }
                3 -> {
                    binding.strDayOfWeek = "수"
                    binding.tvDayOfWeek.setTextColor(Color.GRAY)
                }
                4 -> {
                    binding.strDayOfWeek = "목"
                    binding.tvDayOfWeek.setTextColor(Color.GRAY)
                }
                5 -> {
                    binding.strDayOfWeek = "금"
                    binding.tvDayOfWeek.setTextColor(Color.GRAY)
                }
                6 -> {
                    binding.strDayOfWeek = "토"
                    binding.tvDayOfWeek.setTextColor(Color.BLUE)
                }
                else -> {
                    binding.strDayOfWeek = ""
                    binding.tvDayOfWeek.setTextColor(Color.GRAY)
                }
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
            // calendarItem.date = YYYYYYMMdd
            var day = calendarItem.date.substring(6, 8)
            if(day == "01") {
                val month = calendarItem.date.substring(4, 6)
                day = "$month.$day"
            }

            val df = DecimalFormat("#,###")
            return CalendarItemViewModel(
                day,
                df.format(calendarItem.consumption),
                df.format(calendarItem.income),
                df.format(calendarItem.result)
            )
        }
    }

}