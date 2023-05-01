package com.example.vocabularynote

import com.example.vocabularynote.entity.Note
import com.example.vocabularynote.entity.NoteItem

object Temp {

    val TEMP_NOTE_LIST = listOf(
        Note(0, "title1", "memo1"),
        Note(1, "title2", "memo2"),
        Note(2, "title3", "memo3"),
        Note(3, "title4", "memo4"),
        Note(4, "title5", "memo5"),
        Note(5, "title6", "memo6"),
        Note(6, "title7", "memo7"),
        Note(7, "title8", "memo8"),
    )

    val TEMP_EDIT_NOTE_LIST = listOf(
        NoteItem(1, 1, "key1", "value1"),
        NoteItem(2, 2, "key2", "value2"),
        NoteItem(3, 3, "key3", "value3"),
        NoteItem(4, 4, "key4", "value4"),
        NoteItem(5, 5, "key5", "value5"),
    )

    const val DELAY_SHOW_UI = 50L
    const val TYPE_GAME = 1
    const val TYPE_EDIT = 2
}