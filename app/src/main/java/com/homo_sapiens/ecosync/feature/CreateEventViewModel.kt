package com.homo_sapiens.ecosync.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.homo_sapiens.ecosync.data.model.create.EventDto
import com.homo_sapiens.ecosync.data.repository.create.CreateEventRepositoryImpl
import com.homo_sapiens.ecosync.data.repository.events.EventsRepositoryImpl
import kotlinx.coroutines.launch

class CreateEventViewModel(
    private val createEventsRepository: CreateEventRepositoryImpl
) : ViewModel() {
    fun createEvent(title: String, description: String, userId: String) {
        viewModelScope.launch {
            val event = EventDto(
                title = title,
                description = description,
                createdAt = Timestamp.now()
            )
            createEventsRepository.createEvent(event)
        }
    }
}