package com.example.accountbookuisampling.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemHistoryBinding
import com.example.accountbookuisampling.dataclass.History
import com.example.accountbookuisampling.util.DateUtil
import java.util.*
import kotlin.collections.ArrayList

class RVAdapter(private val list: ArrayList<History>) : RecyclerView.Adapter<RVAdapter.ViewHolder>(){

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RvItemHistoryBinding = RvItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = _list[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    fun addHistory(history: History) {
        _list.add(history)
    }

    inner class ViewHolder(val binding: RvItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            val model = formatToViewModel(history)
            binding.model = model
        }
        private fun formatToViewModel(history: History): HistoryViewModel {

            // History(date=202009201511, category=숙소비, detail=M카드, amount=1700000)
//            Log.e(TAG, history.toString())

            // date=202009201511
            // YYYYMMddhhmm
            val _year = history.date.substring(0, 4)
            val _month = history.date.substring(4, 6)
            val _day = history.date.substring(6, 8)
            val _hour = history.date.substring(8, 10)
            val _minute = history.date.substring(10, 12)

            val day = _day
            val yearMonth = "$_year.$_month"
            val week = DateUtil.getStringDayOfWeek(_year, _month, _day)
            val category = history.category
            val detail = history.detail
            val time = DateUtil.getTime(_hour, _minute)
            val amount = history.amount

            return HistoryViewModel(day, yearMonth, week, category, detail, time, amount)
        }
    }

    data class HistoryViewModel(val day: String, val yearMonth: String, val week: String, val category: String, val detail: String, val time: String, val amount: String)

}

