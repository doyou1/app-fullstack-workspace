package com.example.accountbookuisampling.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemCalendarContentBinding
import com.example.accountbookuisampling.databinding.RvItemCalendarHeadBinding
import com.example.accountbookuisampling.viewmodel.CalendarItemViewModel
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.util.*
import java.text.DecimalFormat

class CalendarRVAdapter(private val list: ArrayList<CalendarItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val headHeight = parent.measuredHeight / CALENDAR_HEAD_HEIGHT_RATIO
        val contentHeight = (parent.measuredHeight - headHeight) / CALENDAR_CONTENT_HEIGHT_RATIO

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
                    binding.strDayOfWeek = TEXT_SUNDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_SUNDAY)
                }
                1 -> {
                    binding.strDayOfWeek = TEXT_MONDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                2 -> {
                    binding.strDayOfWeek = TEXT_TUESDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                3 -> {
                    binding.strDayOfWeek = TEXT_WEDNESDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                4 -> {
                    binding.strDayOfWeek = TEXT_THURSDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                5 -> {
                    binding.strDayOfWeek = TEXT_FRIDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                6 -> {
                    binding.strDayOfWeek = TEXT_SATURDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_SATURDAY)
                }
                else -> {
                    binding.strDayOfWeek = ""
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
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
            if(day == TEXT_FIRST_DAY_OF_MONTH) {
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