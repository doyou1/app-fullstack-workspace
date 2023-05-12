package com.example.vocabularynote

import android.app.Application
import androidx.room.Room
import com.example.vocabularynote.room.AppDataBase
import com.example.vocabularynote.util.Const.DB_NAME

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    val noteDao by lazy {
        db.noteDao()
    }

}