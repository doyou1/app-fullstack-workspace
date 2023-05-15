package com.example.vocabularynote.util

import java.text.SimpleDateFormat
import java.util.Calendar

class DateUtil {

    companion object {
        fun getCurrentTime(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("YYYYMMDDHHmmssSSS")
            return sdf.format(cal.time)
        }
    }
}
