package com.example.vocabularynote.util

import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.room.viewmodel.NoteItemViewModel

class DataUtil {

    companion object {

        fun convertToNoteItemViewModel(list: List<NoteItem>): List<NoteItemViewModel> {
            val result = arrayListOf<NoteItemViewModel>()
            for (item in list) {
                result.add(
                    NoteItemViewModel(
                        id = item.id,
                        noteId = item.noteId,
                        key = item.key,
                        value = item.value
                    )
                )
            }
            return result
        }
    }
}