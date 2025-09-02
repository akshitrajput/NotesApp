package com.example.notesapp.data


import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String, // Changed from val to var
    var content: String,
    var categoryId: String? = null
)
