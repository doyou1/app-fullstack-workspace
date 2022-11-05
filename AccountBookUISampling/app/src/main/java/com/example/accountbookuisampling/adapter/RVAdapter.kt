package com.example.accountbookuisampling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemHistoryBinding
import com.example.accountbookuisampling.dataclass.History

class RVAdapter(private val list: ArrayList<History>) : RecyclerView.Adapter<RVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RvItemHistoryBinding = RvItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = list[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: RvItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            val model = formatToViewModel(history)
            binding.model = model
        }
        private fun formatToViewModel(history: History): HistoryViewModel {

            val day = "20"
            val yearMonth = "2020.09"
            val week = "월요일"
            val category = "숙소비"
            val detail = "M카드"
            val time = "오후 3:11"
            val amount = "1,700,000원"

            return HistoryViewModel(day, yearMonth, week, category, detail, time, amount)
        }
    }

    data class HistoryViewModel(val day: String, val yearMonth: String, val week: String, val category: String, val detail: String, val time: String, val amount: String)

}

