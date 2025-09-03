package com.example.notesapp.data.local

import androidx.room.*
import com.example.notesapp.data.model.Category
import kotlinx.coroutines.flow.Flow
import androidx.room.Delete

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)
}
