package com.example.notesapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar() {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { /* Handle save */ }) {
                Icon(Icons.Default.Check, contentDescription = "Save Note")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle undo */ }) {
                Icon(Icons.Default.Undo, contentDescription = "Undo")
            }
            IconButton(onClick = { /* Handle redo */ }) {
                Icon(Icons.Default.Redo, contentDescription = "Redo")
            }
            IconButton(onClick = { /* Handle share */ }) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
            IconButton(onClick = { /* Handle more options */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More Options")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2D2D2D),
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}
