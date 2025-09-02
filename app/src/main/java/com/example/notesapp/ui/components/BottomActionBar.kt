package com.example.notesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomActionBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.7f))
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Handle text format */ }) {
            Icon(Icons.Outlined.TextFields, contentDescription = "Text Format")
        }
        Divider(modifier = Modifier.height(24.dp).width(1.dp))
        IconButton(onClick = { /* Handle checklist */ }) {
            Icon(Icons.Outlined.CheckBox, contentDescription = "Checklist")
        }
        Divider(modifier = Modifier.height(24.dp).width(1.dp))
        IconButton(onClick = { /* Handle audio */ }) {
            Icon(Icons.Outlined.Mic, contentDescription = "Record Audio")
        }
        Divider(modifier = Modifier.height(24.dp).width(1.dp))
        IconButton(onClick = { /* Handle reminder */ }) {
            Icon(Icons.Outlined.NotificationAdd, contentDescription = "Add Reminder")
        }
        Divider(modifier = Modifier.height(24.dp).width(1.dp))
        IconButton(onClick = { /* Handle image */ }) {
            Icon(Icons.Outlined.Image, contentDescription = "Add Image")
        }
        Divider(modifier = Modifier.height(24.dp).width(1.dp))
        IconButton(onClick = { /* Handle more options */ }) {
            Icon(Icons.Outlined.MoreHoriz, contentDescription = "More")
        }
    }
}
