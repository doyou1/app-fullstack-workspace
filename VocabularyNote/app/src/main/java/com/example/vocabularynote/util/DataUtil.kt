package com.example.vocabularynote.util

import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.room.viewmodel.GameNoteItemExamViewModel
import com.example.vocabularynote.room.viewmodel.GameNoteItemFlipViewModel
import com.example.vocabularynote.room.viewmodel.NoteItemViewModel
import com.example.vocabularynote.util.Const.MAX_SIZE_QUESTION
import org.apache.poi.ss.usermodel.Workbook

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

        fun convertToGameNoteItemFlipViewModel(list: List<NoteItem>): List<GameNoteItemFlipViewModel> {
            val result = arrayListOf<GameNoteItemFlipViewModel>()
            for (item in list) {
                result.add(
                    GameNoteItemFlipViewModel(
                        id = item.id,
                        noteId = item.noteId,
                        key = item.key,
                        value = item.value,
                        showKey = true
                    )
                )
            }
            return result
        }

        fun convertToGameNoteItemExamViewModel(list: List<NoteItem>): List<GameNoteItemExamViewModel> {
            val result = arrayListOf<GameNoteItemExamViewModel>()
            if (list.size < MAX_SIZE_QUESTION) {
                val questions = arrayListOf<String>()
                for (item in list) {
                    questions.add(item.value)
                }
                for (item in list) {
                    result.add(
                        GameNoteItemExamViewModel(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value,
                            questions = questions
                        )
                    )
                }
            } else {
                for (item in list) {
                    val questions = arrayListOf<String>()
                    questions.add(item.value)
                    while (questions.size < MAX_SIZE_QUESTION) {
                        getAnotherValue(list.shuffled(), questions)?.let { anotherValue ->
                            questions.add(anotherValue)
                        }
                    }
                    result.add(
                        GameNoteItemExamViewModel(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value,
                            questions = questions.shuffled()
                        )
                    )
                }
            }


            return result
        }

        fun convertExcelToItems(workbook: Workbook): List<NoteItem>? {
            val sheet = workbook.getSheet(Const.TEXT_RESULT)
            val keyTitle = sheet.getRow(0).getCell(0).toString()
            val valueTitle = sheet.getRow(0).getCell(1).toString()
            return if (keyTitle == Const.TEXT_KEY && valueTitle == Const.TEXT_VALUE) {
                val result = arrayListOf<NoteItem>()
                val keyCellIdx = 0
                val valueCellIdx = 1
                for (i in 1..sheet.lastRowNum) {
                    val row = sheet.getRow(i)
                    val key = row.getCell(keyCellIdx).toString()
                    val value = row.getCell(valueCellIdx).toString()
                    if (key.isNotEmpty() && value.isNotEmpty()) {
                        result.add(NoteItem(key = key, value = value))
                    }
                }
                result
            } else null
        }

        private fun getAnotherValue(
            shuffled: List<NoteItem>,
            prevQuestions: List<String>
        ): String? {
            for (item in shuffled) {
                if (!prevQuestions.contains(item.value)) return item.value
            }
            return null
        }
    }
}