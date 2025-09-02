package com.example.notesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.notesapp.data.Note
import com.example.notesapp.ui.components.*
import com.example.notesapp.ui.theme.LightYellow
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NoteScreen(
    navController: NavController,
    notes: MutableList<Note>,
    noteId: String?,
    categoryId: String?
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = Color(0xFF2D2D2D))
    }

    // Find the existing note based on the noteId
    val existingNote = remember(noteId) { notes.find { it.id == noteId } }

    // Set up the state for title and content. Use the existing note's data if available.
    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var content by remember { mutableStateOf(existingNote?.content ?: "") }

    Scaffold(
        topBar = {
            NoteTopAppBar(onBackClicked = {
                // When the back button is clicked, save the changes.
                if (existingNote != null) {
                    // If the note exists, update its properties. This is now valid.
                    existingNote.title = title
                    existingNote.content = content
                } else if (noteId != null && (title.isNotBlank() || content.isNotBlank())) {
                    // If it's a new note, create it and add it to the list.
                    // We check if there's any content to avoid saving empty notes.
                    val newNote = Note(id = noteId, title = title, content = content, categoryId = categoryId)
                    notes.add(newNote)
                }
                // Navigate back to the previous screen.
                navController.popBackStack()
            })
        },
        bottomBar = { BottomActionBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LightYellow)
        ) {
            DateAndCategoryRow()
            NoteContentFields(
                title = title,
                onTitleChange = { title = it },
                note = content,
                onNoteChange = { content = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            AttachmentsSection()
        }
    }
}
