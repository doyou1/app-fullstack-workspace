package com.example.accountbookuisampling.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(@PrimaryKey val id: Int, val type: Int, val name: String)
