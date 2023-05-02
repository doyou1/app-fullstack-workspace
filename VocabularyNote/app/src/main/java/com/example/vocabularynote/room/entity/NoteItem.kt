package com.example.vocabularynote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val noteId: Int = -1,
    var key: String = "",
    var value: String = ""
)