package com.example.accountbookuisampling.viewmodel

import java.time.DayOfWeek

data class DayViewModel(
    val id: Int,
    val day: String,
    val yearMonth: String,
    val dayOfWeek: String,
    val asset: String,
    val category: String,
    val time: String,
    val historyType: Int,
    val amount: String
)

