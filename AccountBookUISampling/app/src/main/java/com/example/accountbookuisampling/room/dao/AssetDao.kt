package com.example.accountbookuisampling.room.dao

import androidx.room.*
import com.example.accountbookuisampling.room.entities.Asset

@Dao
interface AssetDao {

    @Query("SELECT * FROM Asset")
    fun getAll() : List<Asset>

    @Query("SELECT * FROM Asset WHERE id=:id")
    fun getByid(id: Int) : Asset

    @Insert
    fun insert(asset: Asset)

    @Update
    fun update(asset: Asset)

    @Delete
    fun delete(asset: Asset)
}