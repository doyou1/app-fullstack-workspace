package com.example.timerecord.viewmodel

data class TodoHistoryViewModel(
    val id: Long,
    val todoId: Long,
    // "20230331"
    val targetDate: String,
    // "0900"
    val startTime: String,
    // "1800"
    val endTime: String,
    val viewType: Int,
    val strDayOfWeek: String,
)