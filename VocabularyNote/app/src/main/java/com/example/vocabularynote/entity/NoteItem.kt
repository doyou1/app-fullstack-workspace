package com.example.vocabularynote.entity

data class NoteItem(
    val id: Int = -1,
    val noteId: Int = -1,
    var key: String = "",
    var value: String = ""
)