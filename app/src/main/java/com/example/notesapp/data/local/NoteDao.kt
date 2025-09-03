package com.example.notesapp.data.local

import androidx.room.*
import com.example.notesapp.data.model.Note
import kotlinx.coroutines.flow.Flow
import androidx.room.Delete

@Dao
interface NoteDao {
    // This query correctly fetches only the notes where categoryId is null
    @Query("SELECT * FROM notes WHERE categoryId IS NULL")
    fun getUncategorizedNotes(): Flow<List<Note>>

    // ... other queries remain the same
    @Query("SELECT * FROM notes WHERE categoryId = :categoryId")
    fun getNotesForCategory(categoryId: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: String): Flow<Note?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
