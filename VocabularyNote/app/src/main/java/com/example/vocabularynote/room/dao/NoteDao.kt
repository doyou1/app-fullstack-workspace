package com.example.vocabularynote.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.room.entity.NoteItem

@Dao
interface NoteDao {


    @Query("SELECT * FROM Note")
    fun getNoteAll(): List<Note>

    @Query("SELECT * FROM NoteItem WHERE noteId = :noteId")
    fun getNoteItemAllByNoteId(noteId: Int): List<NoteItem>

    @Insert
    fun insertNote(item: Note)

    @Insert
    fun insertNoteItem(item: NoteItem)

    @Insert
    fun insertNoteAll(item: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteItemAll(item: List<NoteItem>)

    @Update
    fun updateNote(item: Note)

    @Update
    fun updateNoteItem(item: NoteItem)

    @Update
    fun updateNoteAll(item: List<Note>)

    @Update
    fun updateNoteItemAll(item: List<NoteItem>)

    @Delete
    fun deleteNote(item: Note)

    @Delete
    fun deleteNoteItem(item: NoteItem)

    @Delete
    fun deleteNoteAll(item: List<Note>)

    @Delete
    fun deleteNoteItemAll(item: List<NoteItem>)

}