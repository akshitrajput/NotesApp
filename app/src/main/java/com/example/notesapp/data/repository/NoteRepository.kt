package com.example.notesapp.data.repository

import com.example.notesapp.data.local.CategoryDao
import com.example.notesapp.data.local.NoteDao
import com.example.notesapp.data.model.Category
import com.example.notesapp.data.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao
) {
    fun getUncategorizedNotes() = noteDao.getUncategorizedNotes()
    fun getAllCategories() = categoryDao.getAllCategories()
    fun getNotesForCategory(categoryId: String) = noteDao.getNotesForCategory(categoryId)
    fun getNoteById(noteId: String) = noteDao.getNoteById(noteId)
    suspend fun saveNote(note: Note) = noteDao.upsertNote(note)
    suspend fun createCategory(category: Category) = categoryDao.insertCategory(category)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)
}
