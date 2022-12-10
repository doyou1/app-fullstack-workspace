package com.example.accountbookuisampling.registerinput.adapter.consumption

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.fragment.registerinput.consumption.RegisterConsumptionInputDateFragment
import com.example.accountbookuisampling.registerinput.dataclass.InputDateItem
import com.example.accountbookuisampling.util.*

class ConsumptionInputDateRVAdapter(
    private val date: String,
    list: ArrayList<InputDateItem>,
    private val parentBinding: FragmentConsumptionBinding,
    private val inputFragment: RegisterConsumptionInputDateFragment,
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

//        val headHeight = parent.measuredHeight / CALENDAR_HEAD_HEIGHT_RATIO
//        val contentHeight = (parent.measuredHeight - headHeight) / CALENDAR_CONTENT_HEIGHT_RATIO

        when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvItemDateInputHeadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
//                binding.root.layoutParams.height = headHeight
                return HeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT -> {
                val binding = RvItemDateInputContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
//                binding.root.layoutParams.height = contentHeight
                return ContentViewHolder(binding)
            }
            else -> {
                throw NotImplementedError()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // yyyyMMdd
        val item = _list[position]

        when (item.viewType) {
            TYPE_CALENDAR_HEAD -> {
                (holder as HeadViewHolder).bind(item)
            }
            TYPE_CALENDAR_CONTENT -> {
                (holder as ContentViewHolder).bind(item)
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

    inner class HeadViewHolder(private val binding: RvItemDateInputHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InputDateItem) {
            when (item.dayOfWeekSequence) {
                0 -> {
                    binding.tvDay.text = TEXT_SUNDAY
                    binding.tvDay.setTextColor(COLOR_SUNDAY)
                }
                1 -> {
                    binding.tvDay.text = TEXT_MONDAY
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                2 -> {
                    binding.tvDay.text = TEXT_TUESDAY
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                3 -> {
                    binding.tvDay.text = TEXT_WEDNESDAY
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                4 -> {
                    binding.tvDay.text = TEXT_THURSDAY
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                5 -> {
                    binding.tvDay.text = TEXT_FRIDAY
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                6 -> {
                    binding.tvDay.text = TEXT_SATURDAY
                    binding.tvDay.setTextColor(COLOR_SATURDAY)
                }
                else -> {
                    binding.tvDay.text = ""
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
            }
        }
    }
    inner class ContentViewHolder(private val binding: RvItemDateInputContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InputDateItem) {
            val _year = date.substring(0, 4)
            val _month = date.substring(5, 7)
            val _day = date.substring(8, 10)

            val year = item.date.substring(0, 4)
            val month = item.date.substring(4, 6)
            val day = item.date.substring(6, 8)

            if(day == "01") {
                binding.tvDay.text = "$month.$day"
            } else {
                binding.tvDay.text = day
            }


            when (item.dayOfWeekSequence) {
                0 -> {
                    binding.tvDay.setTextColor(COLOR_SUNDAY)
                }
                1 -> {
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                2 -> {
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                3 -> {
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                4 -> {
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                5 -> {
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
                6 -> {
                    binding.tvDay.setTextColor(COLOR_SATURDAY)
                }
                else -> {
                    binding.tvDay.setTextColor(COLOR_WEEKDAY)
                }
            }

            if(_year == year && _month == month && _day == day) {
                binding.tvDay.setTextColor(Color.WHITE)
                binding.root.setBackgroundColor(inputFragment.resources.getColor(R.color.orange))
            }

            binding.tvDay.setOnClickListener {
                inputFragment.changeDate("$year/$month/$day")
            }
        }
    }
}