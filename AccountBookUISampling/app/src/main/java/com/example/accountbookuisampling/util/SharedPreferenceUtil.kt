package com.example.accountbookuisampling.util

import android.app.Application
import com.example.accountbookuisampling.application.BaseApplication

class SharedPreferenceUtil {

    companion object {
        var application: BaseApplication? = null
        fun setDate(date: String) {
            val sharedPreference = application?.getSharedPreferences(TEXT_DATE, Application.MODE_PRIVATE)
            sharedPreference?.edit()?.putString(TEXT_DATE, date)?.apply()
        }

        fun getDate() : String? {
            val sharedPreference = application?.getSharedPreferences(TEXT_DATE, Application.MODE_PRIVATE)
            return sharedPreference?.getString(TEXT_DATE, TEXT_EMPTY)
        }

        fun setCurrentItem(id: Int) {
            val sharedPreference = application?.getSharedPreferences(TEXT_CURRENT_ITEM, Application.MODE_PRIVATE)
            sharedPreference?.edit()?.putInt(TEXT_CURRENT_ITEM, id)?.apply()
        }

        fun getCurrentItem() : Int? {
            val sharedPreference = application?.getSharedPreferences(TEXT_CURRENT_ITEM, Application.MODE_PRIVATE)
            return sharedPreference?.getInt(TEXT_CURRENT_ITEM, -1)
        }

    }
}