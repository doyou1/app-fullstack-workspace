package com.example.timerecord

import com.example.timerecord.entity.Todo

object Const {
    val TEMP_TODO_LIST = listOf(
        Todo(
            0,
            "Company",
            "20230331",
            "0900",
            "1800",
            listOf(true, true, true, true, true, false, false)
        ),
        Todo(
            1,
            "Gym",
            "20230225",
            "1900",
            "2030",
            listOf(false, false, false, false, false, true, true)
        ),
        Todo(
            1,
            "Study",
            "20230115",
            "0600",
            "0700",
            listOf(true, true, true, true, true, true, true)
        )
    )
}