package com.example.accountbookuisampling.room.dao

import androidx.room.*
import com.example.accountbookuisampling.room.entities.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM Category")
    fun getAll() : List<Category>

    @Query("SELECT * FROM Category WHERE id=:id")
    fun getByid(id: Int) : Category

    @Query("SELECT * FROM Category WHERE type=:type")
    fun getByType(type: Int) : Category

    @Insert
    fun insert(category: Category)
    @Insert
    fun insertAll(categories: ArrayList<Category>)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)
}