package com.example.accountbookuisampling.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("assetId"),
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
        ),
    ]
)
data class History(
    @PrimaryKey val id: Int,
    val date: String,
    val historyType: Int,
    val assetId: Int,
    val categoryId: Int,
    val amount: Int,
    val detail: String?,
    val additionDetail: String?,
    val image: String?
)
