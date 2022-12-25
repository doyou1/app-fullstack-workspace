package com.example.accountbookuisampling.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {

        fun getStringDayOfWeek(date: String): String {
            val year = date.substring(0, 4)
            val month = date.substring(4, 6)
            val day = date.substring(6, 8)

            val calendar = Calendar.getInstance()
            calendar.set(year.toInt(), month.toInt() - 1, day.toInt())

            val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
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

        fun getStringTime(time: String): String {
            val _hour = time.substring(0, 2)
            val _minute = time.substring(2, 4)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, _hour.toInt())
            calendar.set(Calendar.MINUTE, _minute.toInt())

            val amPm = if (calendar.get(Calendar.HOUR_OF_DAY) < 12) TEXT_AM
            else TEXT_PM
            val hour = if (calendar.get(Calendar.HOUR) < 10) "0${calendar.get(Calendar.HOUR)}"
            else calendar.get(Calendar.HOUR).toString()
            val minute = if (calendar.get(Calendar.MINUTE) < 10) "0${calendar.get(Calendar.MINUTE)}"
            else calendar.get(Calendar.MINUTE)

            return "$amPm $hour:$minute"
        }

        fun getDateList(): ArrayList<String> {
            val dateList = arrayListOf<String>()

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val dayOfWeekByMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK)

            // 달력은 첫번째 날 설정(1열 1행)
            calendar.set(
                Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) - (dayOfWeekByMonthFirstDay - 1)
            )

            for (i in 0 until 35) {
                val _year = calendar.get(Calendar.YEAR)
                val _month =
                    if ((calendar.get(Calendar.MONTH) + 1) < 10) "0${calendar.get(Calendar.MONTH) + 1}"
                    else calendar.get(Calendar.MONTH) + 1
                val _day =
                    if ((calendar.get(Calendar.DAY_OF_MONTH)) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
                    else calendar.get(Calendar.DAY_OF_MONTH)

                dateList.add("$_year$_month$_day")

                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }
            return dateList
        }

        // date : yyyy/MM/dd
        fun getDateList(yearMonth: String?): ArrayList<String> {
            val dateList = arrayListOf<String>()
            val date = if (yearMonth?.isNotEmpty() == true) {
                yearMonth!!
            } else {
                // if yearMonth null → set current year month
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = String.format("%02d", cal.get(Calendar.MONTH) + 1)
                "$year$month"
            }

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val year = date.substring(0, 4)
            val month = date.substring(4, 6)
            calendar.set(Calendar.YEAR, year.toInt())
            calendar.set(Calendar.MONTH, month.toInt() - 1)

            val dayOfWeekByMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK)

            // 달력은 첫번째 날 설정(1열 1행)
            calendar.set(
                Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) - (dayOfWeekByMonthFirstDay - 1)
            )

            for (i in 0 until CALENDAR_CONTENT_SIZE) {
                val _year = calendar.get(Calendar.YEAR)
                val _month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
                val _day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
                dateList.add("$_year$_month$_day")
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }
            return dateList
        }

        fun getTodayText(): String {
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyyMMdd")
            val date = sdf.format(calendar.time)

            val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
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

        fun getDateText(value: String): String {
            val yyyy = value.substring(0, 4).toInt()
            val MM = value.substring(4, 6).toInt()
            val dd = value.substring(6, 8).toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, yyyy)
            calendar.set(Calendar.MONTH, MM - 1)
            calendar.set(Calendar.DAY_OF_MONTH, dd)

            val sdf = SimpleDateFormat("yyyyMMdd")
            val date = sdf.format(calendar.time)

            val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
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
        fun getMonth(date: String): String {
            return date.substring(5, 7)
        }

        fun getPrevMonthDate(value: String): String {
            val yyyy = value.substring(0, 4).toInt()
            val MM = value.substring(4, 6).toInt()
            val dd = value.substring(6, 8).toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, yyyy)
            calendar.set(Calendar.MONTH, MM - 2)
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val sdf = SimpleDateFormat("yyyyMMdd")
            val date = sdf.format(calendar.time)

            return date
        }

        fun getNextMonthDate(value: String): String {
            val yyyy = value.substring(0, 4).toInt()
            val MM = value.substring(4, 6).toInt()
            val dd = value.substring(6, 8).toInt()

            val calendar = Calendar.getInstance()
            calendar.set(yyyy, MM, 1)
            val sdf = SimpleDateFormat("yyyyMMdd")
            val date = sdf.format(calendar.time)

            return date
        }

        fun getSundayByMonday(monday: String): String {
            // yyyyMMdd
            val yyyy = monday.substring(0, 4).toInt()
            val MM = monday.substring(4, 6).toInt() - 1
            val dd = monday.substring(6, 8).toInt()
            val calendar = Calendar.getInstance()
            calendar.set(yyyy, MM, dd)

            // monday + 6 = sunday
            calendar.set(Calendar.DAY_OF_MONTH, dd + 6)

            val sdf = SimpleDateFormat("yyyyMMdd")
            return sdf.format(calendar.time)
        }

    }
}