package com.homo_sapiens.ecosync.feature.events

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.homo_sapiens.ecosync.feature.CreateEventViewModel

@Composable
fun CreateEventScreen(viewModel: CreateEventViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var coverImage by remember { mutableStateOf("") }

    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        TextField(
            value = coverImage,
            onValueChange = { coverImage = it },
            label = { Text("Cover Image URL") }
        )
        Button(onClick = { viewModel.createEvent(title, description, coverImage) }) {
            Text("Submit Event")
        }
    }
}