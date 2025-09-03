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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        topBar = { TopAppBar(title = { Text("Notes App", color = Color.White, modifier = Modifier.padding(16.dp)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showChoiceDialog = true },containerColor = Color(0xFF202124), contentColor = Color.White) {
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
                Text("Notes", color = Color.DarkGray, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(randomNotes) { note ->
                NoteItem(
                    note = note,
                    onClick = { navController.navigate(Screen.Note.createRoute(note.id)) },
                    onLongClick = { itemToDelete = note }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Categories", color = Color.DarkGray,style = MaterialTheme.typography.bodyLarge)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: Note, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        )
    ) {
        Text(text = note.title.ifBlank { "Untitled Note" }, style = MaterialTheme.typography.bodyLarge,modifier = Modifier.padding(16.dp),)
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
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(16.dp),)
        }
    }
}

@Composable
fun DeleteConfirmationDialog(item: Any?, onConfirm: () -> Unit, onDismiss: () -> Unit, backgroundColor: Color = Color.White) {
    val name = when (item) {
        is Note -> item.title.ifBlank { "this note" }
        is Category -> item.name
        else -> ""
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete '$name'?", fontSize = 18.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) },
        text = { Text("Are you sure you want to permanently delete this item?",  fontSize = 15.sp ,style = MaterialTheme.typography.bodyLarge) },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete",  fontSize = 16.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel",  fontSize = 16.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) }
        }
    )
}

@Composable
fun ChoiceDialog(onDismiss: () -> Unit, onCreateNote: () -> Unit, onCreateCategory: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New",  fontSize = 18.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) },
        text = { Text("What would you like to create?", fontSize = 18.sp ,style = MaterialTheme.typography.bodyLarge) },
        confirmButton = { TextButton(onClick = onCreateNote) { Text("Note",  fontSize = 16.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) } },
        dismissButton = { TextButton(onClick = onCreateCategory) { Text("Category",  fontSize = 16.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) } }
    )
}

@Composable
fun NewCategoryDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Category", fontSize = 18.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) },
        text = { TextField(value = text, onValueChange = { text = it }, label = { Text("Category Name",  fontSize = 14.sp , style = MaterialTheme.typography.bodyLarge) }) },
        confirmButton = { Button(onClick = { onConfirm(text.text) }) { Text("Create",  fontSize = 18.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", fontSize = 18.sp , fontWeight = FontWeight.Bold,style = MaterialTheme.typography.bodyLarge) } }
    )
}
