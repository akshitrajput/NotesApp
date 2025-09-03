package com.example.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.data.model.Category
import com.example.notesapp.data.model.Note

@Database(entities = [Note::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
}
