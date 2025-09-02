package com.example.notesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.notesapp.ui.components.*
import com.example.notesapp.ui.theme.LightYellow
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NoteScreen() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0xFF2D2D2D) // Dark color for the status bar
        )
    }

    var title by remember { mutableStateOf("Title") }
    var note by remember { mutableStateOf("Note here") }

    Scaffold(
        topBar = { NoteTopAppBar() },
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
                note = note,
                onNoteChange = { note = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            AttachmentsSection()
        }
    }
}
