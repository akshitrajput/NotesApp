@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.notesapp.ui.screens.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.data.model.Note
import com.example.notesapp.navigation.Screen
import com.example.notesapp.ui.screens.home.DeleteConfirmationDialog
import com.example.notesapp.ui.screens.home.NoteItem
import java.util.UUID

@Composable
fun CategoryScreen(
    navController: NavController,
    categoryName: String,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val categoryId = viewModel.categoryId
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val newNoteId = UUID.randomUUID().toString()
                navController.navigate(Screen.Note.createRoute(newNoteId, categoryId))
            },containerColor = Color(0xFF202124), contentColor = Color.White) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClick = { navController.navigate(Screen.Note.createRoute(note.id, categoryId)) },
                    onLongClick = { noteToDelete = note }
                )
            }
        }
    }

    if (noteToDelete != null) {
        DeleteConfirmationDialog(
            item = noteToDelete,
            onConfirm = {
                viewModel.deleteNote(noteToDelete!!)
                noteToDelete = null
            },
            onDismiss = {
                noteToDelete = null
            },
            backgroundColor = Color(0xFFFFC107)
        )
    }
}
