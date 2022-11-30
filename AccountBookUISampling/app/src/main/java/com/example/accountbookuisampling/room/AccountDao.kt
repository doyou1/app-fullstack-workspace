package com.example.accountbookuisampling.room

import androidx.room.*
import com.example.accountbookuisampling.dataclass.Account

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAll() : List<Account>

    @Query("SELECT * FROM account WHERE uid=:uid")
    fun getByUid(uid: Int) : Account

    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)
}