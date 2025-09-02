package com.example.notesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttachmentsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AttachmentItem(
            icon = Icons.Default.PictureAsPdf,
            text = "Supernotes.pdf",
            size = "33kb"
        )
        AttachmentItem(
            icon = Icons.Default.Mic,
            text = "Supernotes...",
            size = "33kb"
        )
    }
}

@Composable
fun AttachmentItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, size: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(text, fontSize = 12.sp, color = Color.Black)
            Text(size, fontSize = 10.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.Close, contentDescription = "Remove", modifier = Modifier.size(16.dp))
    }
}
