package com.example.accountbookuisampling.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.accountbookuisampling.room.AppDataBase
import com.example.accountbookuisampling.util.*

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

        // init
//        val pref = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
//        val isInit =  pref.getBoolean(TEXT_INIT, false)
//        if(!isInit) {
//            assetDao.insertAll(INIT_ASSET_LIST)
//            categoryDao.insertAll(INIT_CATEGORY_LIST)
//            pref.edit().putBoolean(TEXT_INIT, true)
//        }
    }

}