package com.example.accountbookuisampling.application

import android.app.Application
import androidx.room.Room
import com.example.accountbookuisampling.room.AppDataBase
import com.example.accountbookuisampling.util.DB_NAME

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
    }

    val accountDao by lazy {
        db.accountDao()
    }
}