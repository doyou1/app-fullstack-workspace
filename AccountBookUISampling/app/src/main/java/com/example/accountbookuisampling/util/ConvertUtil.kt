package com.example.accountbookuisampling.util

class ConvertUtil {

    companion object {
        fun getIdByTitle(title: String) : Int {
            return when (title) {
                TITLE_DAY -> ID_DAY
                TITLE_CALENDAR -> ID_CALENDAR
                TITLE_WEEKS -> ID_WEEKS
                TITLE_MONTHS -> ID_MONTHS
                TITLE_SUMMARY ->  ID_SUMMARY
                else -> throw NotImplementedError()
            }
        }
    }
}