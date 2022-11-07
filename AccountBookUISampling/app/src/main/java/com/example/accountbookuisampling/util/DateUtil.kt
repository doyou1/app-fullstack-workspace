package com.example.accountbookuisampling.util

import java.util.*

class DateUtil {

    companion object {
        fun getStringDayOfWeek(_year: String, _month: String, _day: String) : String {
            val calendar = Calendar.getInstance()
            calendar.set(_year.toInt(), _month.toInt() - 1, _day.toInt())

            val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> "일요일"
                2 -> "월요일"
                3 -> "화요일"
                4 -> "수요일"
                5 -> "목요일"
                6 -> "금요일"
                7 -> "토요일"
                else -> ""
            }
            return dayOfWeek
        }

        fun getTime(_hour: String, _minute: String) : String {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, _hour.toInt())
            calendar.set(Calendar.MINUTE, _minute.toInt())

            val amPm = if(calendar.get(Calendar.HOUR_OF_DAY) < 12) "오전"
            else "오후"
            val hour = if(calendar.get(Calendar.HOUR) < 10) "0${calendar.get(Calendar.HOUR)}"
            else calendar.get(Calendar.HOUR).toString()
            val minute = if(calendar.get(Calendar.MINUTE) < 10) "0${calendar.get(Calendar.MINUTE)}"
            else calendar.get(Calendar.MINUTE)

            return "$amPm $hour:$minute"
        }

        fun getDateList() : ArrayList<String> {
            val dateList = arrayListOf<String>()

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val dayOfWeekByMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK)

            // 달력은 첫번째 날 설정(1열 1행)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - (dayOfWeekByMonthFirstDay - 1))

            for (i in 0 until 35) {
                val _year = calendar.get(Calendar.YEAR)
                val _month = if((calendar.get(Calendar.MONTH) + 1) < 10) "0${calendar.get(Calendar.MONTH) + 1}"
                else calendar.get(Calendar.MONTH) + 1
                val _day = if((calendar.get(Calendar.DAY_OF_MONTH)) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
                else calendar.get(Calendar.DAY_OF_MONTH)

                dateList.add("$_year$_month$_day")

                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }
            return dateList
        }
    }
}