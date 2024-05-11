package com.homo_sapiens.ecosync.data.state.events

import com.google.firebase.Timestamp
import com.homo_sapiens.ecosync.data.model.create.Event
import java.util.Date




data class EventsState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasNext: Boolean = true
) {
    val showInitialLoading = isLoading && events.isEmpty()
    val showEmptyScreen = hasNext.not() && events.isEmpty() && isLoading.not()

    // This is for force update to state flow
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
