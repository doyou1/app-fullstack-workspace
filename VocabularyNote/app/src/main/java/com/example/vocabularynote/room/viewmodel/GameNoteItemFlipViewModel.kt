package com.example.vocabularynote.room.viewmodel

data class GameNoteItemFlipViewModel(
    val id: Long = -1,
    val noteId: Long = -1,
    var key: String = "",
    var value: String = "",
    var showKey: Boolean = true
)