@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.notesapp.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.data.model.Category
import com.example.notesapp.data.model.Note
import com.example.notesapp.navigation.Screen
import java.util.UUID

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val randomNotes by viewModel.randomNotes.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var showChoiceDialog by remember { mutableStateOf(false) }
    var showCreateCategoryDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<Any?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Supernotes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showChoiceDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Create")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply padding from Scaffold
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Section 1: Uncategorized Notes
            item {
                Text("Random Notes", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(randomNotes) { note ->
                NoteItem(
                    note = note,
                    onClick = { navController.navigate(Screen.Note.createRoute(note.id)) },
                    onLongClick = { itemToDelete = note }
                )
            }

            // Section 2: Categories
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Categories", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onClick = { navController.navigate(Screen.Category.createRoute(category.id, category.name)) },
                    onLongClick = { itemToDelete = category }
                )
            }
        }
    }

    // --- Dialogs for Creation ---
    if (showChoiceDialog) {
        ChoiceDialog(
            onDismiss = { showChoiceDialog = false },
            onCreateNote = {
                showChoiceDialog = false
                navController.navigate(Screen.Note.createRoute(UUID.randomUUID().toString()))
            },
            onCreateCategory = {
                showChoiceDialog = false
                showCreateCategoryDialog = true
            }
        )
    }

    if (showCreateCategoryDialog) {
        NewCategoryDialog(
            onDismiss = { showCreateCategoryDialog = false },
            onConfirm = { categoryName ->
                viewModel.createCategory(categoryName)
                showCreateCategoryDialog = false
            }
        )
    }

    // --- Dialog for Deletion ---
    if (itemToDelete != null) {
        DeleteConfirmationDialog(
            item = itemToDelete,
            onConfirm = {
                when (val item = itemToDelete) {
                    is Note -> viewModel.deleteNote(item)
                    is Category -> viewModel.deleteCategory(item)
                }
                itemToDelete = null
            },
            onDismiss = { itemToDelete = null }
        )
    }
}


// --- Reusable UI Components for this Screen ---

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: Note, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Text(text = note.title.ifBlank { "Untitled Note" }, modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(category: Category, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category.name)
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun DeleteConfirmationDialog(item: Any?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val name = when (item) {
        is Note -> item.title.ifBlank { "this note" }
        is Category -> item.name
        else -> ""
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete '$name'?") },
        text = { Text("Are you sure you want to permanently delete this? This action cannot be undone.") },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun ChoiceDialog(onDismiss: () -> Unit, onCreateNote: () -> Unit, onCreateCategory: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New") },
        text = { Text("What would you like to create?") },
        confirmButton = { TextButton(onClick = onCreateNote) { Text("Note") } },
        dismissButton = { TextButton(onClick = onCreateCategory) { Text("Category") } }
    )
}

@Composable
fun NewCategoryDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Category") },
        text = { TextField(value = text, onValueChange = { text = it }, label = { Text("Category Name") }) },
        confirmButton = { Button(onClick = { onConfirm(text.text) }) { Text("Create") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
