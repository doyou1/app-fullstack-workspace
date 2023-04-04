package com.example.timerecord.util

import com.example.timerecord.util.Const.TYPE_CALENDAR_CONTENT
import com.example.timerecord.viewmodel.TodoHistoryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class Util {

    companion object {
        val emptyTodoHistoryViewModel =
            TodoHistoryViewModel(0, 0, "", "", "", TYPE_CALENDAR_CONTENT, "")

        fun fillTodoHistoryViewModelList(input: List<TodoHistoryViewModel>): ArrayList<TodoHistoryViewModel> {
            val c = Calendar.getInstance()
            c.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayOfWeekWithCalendar = c.get(Calendar.DAY_OF_WEEK)
            // (row,col):(1,1)
            c.set(
                Calendar.DAY_OF_MONTH,
                c.get(Calendar.DAY_OF_MONTH) - (firstDayOfWeekWithCalendar - 1)
            )
            val sdf = SimpleDateFormat("yyyyMMdd")
            val result = arrayListOf<TodoHistoryViewModel>()
            for (i in 0 until 35) {
                val yyyyMMdd = sdf.format(c.time)
                val filter = input.filter { item -> item.targetDate == yyyyMMdd }
                if (filter.isEmpty()) {
                    result.add(
                        TodoHistoryViewModel(
                            0,
                            0,
                            yyyyMMdd,
                            "",
                            "",
                            TYPE_CALENDAR_CONTENT,
                            ""
                        )
                    )
                } else {
                    result.add(
                        filter[0]
                    )
                }
                c.add(Calendar.DAY_OF_MONTH, 1)
            }
            return result
        }
        fun getToday(): String = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time)
        fun getFormattedToday(): String = SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().time)

    }

}