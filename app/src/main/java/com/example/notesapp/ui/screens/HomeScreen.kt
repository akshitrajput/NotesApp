package com.example.notesapp.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.example.notesapp.data.Category
import com.example.notesapp.data.Note
import com.example.notesapp.navigation.Screen
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    notes: List<Note>,
    categories: List<Category>,
    onAddCategory: (Category) -> Unit
) {
    var showChoiceDialog by remember { mutableStateOf(false) }
    var showCreateCategoryDialog by remember { mutableStateOf(false) }
    val randomNotes = notes.filter { it.categoryId == null }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Supernotes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showChoiceDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Create")
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
            item {
                Text("Random Notes", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(randomNotes) { note ->
                NoteItem(note = note, onClick = {
                    navController.navigate(Screen.Note.createRoute(note.id))
                })
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Categories", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(categories) { category ->
                CategoryItem(category = category, onClick = {
                    navController.navigate(Screen.Category.createRoute(category.id))
                })
            }
        }
    }

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
                onAddCategory(Category(name = categoryName))
                showCreateCategoryDialog = false
            }
        )
    }
}


@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Text(text = note.title, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category.name)
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = "Open Category",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ChoiceDialog(
    onDismiss: () -> Unit,
    onCreateNote: () -> Unit,
    onCreateCategory: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New") },
        text = { Text("What would you like to create?") },
        confirmButton = {
            TextButton(onClick = onCreateNote) { Text("Note") }
        },
        dismissButton = {
            TextButton(onClick = onCreateCategory) { Text("Category") }
        }
    )
}

@Composable
fun NewCategoryDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Category") },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Category Name") }
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(text.text) }) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
