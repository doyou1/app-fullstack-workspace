package com.example.accountbookuisampling.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.accountbookuisampling.room.dao.AssetDao
import com.example.accountbookuisampling.room.dao.CategoryDao
import com.example.accountbookuisampling.room.dao.HistoryDao
import com.example.accountbookuisampling.room.entities.Asset
import com.example.accountbookuisampling.room.entities.Category
import com.example.accountbookuisampling.room.entities.History

@Database(entities = [History::class, Asset::class, Category::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun categoryDao(): CategoryDao
    abstract fun historyDao(): HistoryDao
}