package com.example.accountbookuisampling.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset(@PrimaryKey val id: Int, val groupId: Int, val level: Int, val name: String, val amount: Int, val memo: String?)
