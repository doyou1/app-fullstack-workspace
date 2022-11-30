package com.example.accountbookuisampling.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.accountbookuisampling.dataclass.Account

@Database(entities = [Account::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun accountDao() : AccountDao
}