package com.example.vocabularynote.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vocabularynote.room.dao.NoteDao
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.room.entity.NoteItem

@Database(entities = [Note::class, NoteItem::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}