package com.example.accountbookuisampling.application

import android.app.Application
import androidx.room.Room
import com.example.accountbookuisampling.room.AppDataBase
import com.example.accountbookuisampling.util.DB_NAME
import com.example.accountbookuisampling.util.DateUtil
import com.example.accountbookuisampling.util.SharedPreferenceUtil

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
    }

    val assetDao by lazy {
        db.assetDao()
    }

    val categoryDao by lazy {
        db.categoryDao()
    }

    val historyDao by lazy {
        db.historyDao()
    }

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceUtil.application = this
        SharedPreferenceUtil.setDate(DateUtil.getToday())
        SharedPreferenceUtil.setCurrentItem(0)
    }

}