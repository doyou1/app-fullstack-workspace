package com.example.pedometer_app.util

import java.util.*

class DateUtil {

    companion object {
        fun getStrNow(): String {
            val now = Calendar.getInstance()
            val YYYY = now.get(Calendar.YEAR)
            val MM = if(now.get(Calendar.MONTH) + 1 > 9) {
                now.get(Calendar.MONTH) + 1
            } else {
                "0${now.get(Calendar.MONTH) + 1}"
            }
            val DD = if(now.get(Calendar.DAY_OF_MONTH) > 9) {
                now.get(Calendar.DAY_OF_MONTH)
            } else {
                "0${now.get(Calendar.DAY_OF_MONTH)}"
            }
            val hh = if(now.get(Calendar.HOUR) > 9) {
                now.get(Calendar.HOUR)
            } else {
                "0${now.get(Calendar.HOUR)}"
            }
            val mm = if(now.get(Calendar.MINUTE) > 9) {
                now.get(Calendar.MINUTE)
            } else {
                "0${now.get(Calendar.MINUTE)}"
            }
            val ss = if(now.get(Calendar.SECOND) > 9) {
                now.get(Calendar.SECOND)
            } else {
                "0${now.get(Calendar.SECOND)}"
            }

            return "$YYYY$MM$DD $hh$mm$ss"
        }
    }
}