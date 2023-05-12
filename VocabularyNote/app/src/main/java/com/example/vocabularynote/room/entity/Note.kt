package com.example.vocabularynote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(@PrimaryKey(autoGenerate = true) val id: Long, val title: String, val memo: String?, val useTranslation: Boolean = false)
