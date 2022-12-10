package com.example.accountbookuisampling.room.dao

import androidx.room.*
import com.example.accountbookuisampling.room.entities.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History")
    fun getAll(): List<History>

    @Query("SELECT * FROM History WHERE id=:id")
    fun getByid(id: Int): History

    @Query("SELECT * FROM History WHERE date like :date || '%'")
    fun getByDate(date: String?): List<History>

    @Insert
    fun insert(history: History)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)
}