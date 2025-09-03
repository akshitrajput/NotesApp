package com.example.notesapp.ui.screens.note


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.data.model.Note
import com.example.notesapp.ui.components.*
import com.example.notesapp.ui.theme.LightYellow
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val note by viewModel.note.collectAsState()

    NoteScreenContent(
        navController = navController,
        note = note,
        onSave = { title, content -> viewModel.saveNote(title, content) }
    )
}

@Composable
fun NoteScreenContent(
    navController: NavController,
    note: Note?,
    onSave: (title: String, content: String) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = Color(0xFF2D2D2D))
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // Update the local state when the note from the ViewModel changes
    LaunchedEffect(note) {
        if (note != null) {
            title = note.title
            content = note.content
        }
    }

    Scaffold(
        topBar = {
            NoteTopAppBar(onBackClicked = {
                onSave(title, content)
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
            DateAndCategoryRow() // This can be enhanced to show the real date/category
            NoteContentFields(
                title = title,
                onTitleChange = { title = it },
                note = content,
                onNoteChange = { content = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            AttachmentsSection() // This remains a static UI component for now
        }
    }
}
