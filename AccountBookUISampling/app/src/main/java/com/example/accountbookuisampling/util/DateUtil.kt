package com.example.accountbookuisampling.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun getStringDayOfWeek(_year: String, _month: String, _day: String) : String {
            val calendar = Calendar.getInstance()
            calendar.set(_year.toInt(), _month.toInt() - 1, _day.toInt())

            val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> TEXT_SUNDAY
                2 -> TEXT_MONDAY
                3 -> TEXT_TUESDAY
                4 -> TEXT_WEDNESDAY
                5 -> TEXT_THURSDAY
                6 -> TEXT_FRIDAY
                7 -> TEXT_SATURDAY
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

        // date : yyyy/MM/dd
        fun getDateList(date: String) : ArrayList<String> {
            val yyyy = date.substring(0, 4).toInt()
            val MM = date.substring(5, 7).toInt()
            val dd = date.substring(8, 10).toInt()

            val dateList = arrayListOf<String>()

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, yyyy)
            calendar.set(Calendar.MONTH, MM - 1)
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

        fun getTodayText() : String {
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.format(calendar.time)

            val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> TEXT_SUNDAY
                2 -> TEXT_MONDAY
                3 -> TEXT_TUESDAY
                4 -> TEXT_WEDNESDAY
                5 -> TEXT_THURSDAY
                6 -> TEXT_FRIDAY
                7 -> TEXT_SATURDAY
                else -> ""
            }

            return "$date ($dayOfWeek)"
        }

        fun getDateText(value: String) : String {
            val yyyy = value.substring(0, 4).toInt()
            val MM = value.substring(5, 7).toInt()
            val dd = value.substring(8, 10).toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, yyyy)
            calendar.set(Calendar.MONTH, MM - 1)
            calendar.set(Calendar.DAY_OF_MONTH, dd)

            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.format(calendar.time)

            val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> TEXT_SUNDAY
                2 -> TEXT_MONDAY
                3 -> TEXT_TUESDAY
                4 -> TEXT_WEDNESDAY
                5 -> TEXT_THURSDAY
                6 -> TEXT_FRIDAY
                7 -> TEXT_SATURDAY
                else -> ""
            }

            return "$date ($dayOfWeek)"
        }

        // date : yyyy/MM/dd
        fun getMonth(date: String) : String {
            return date.substring(5, 7)
        }

        fun getPrevMonthDate(value: String) : String {
            val yyyy = value.substring(0, 4).toInt()
            val MM = value.substring(5, 7).toInt()
            val dd = value.substring(8, 10).toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, yyyy)
            calendar.set(Calendar.MONTH, MM - 2)
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.format(calendar.time)

            return date
        }

        fun getNextMonthDate(value: String) : String {
            val yyyy = value.substring(0, 4).toInt()
            val MM = value.substring(5, 7).toInt()
            val dd = value.substring(8, 10).toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, yyyy)
            calendar.set(Calendar.MONTH, MM)
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.format(calendar.time)

            return date
        }

    }
}