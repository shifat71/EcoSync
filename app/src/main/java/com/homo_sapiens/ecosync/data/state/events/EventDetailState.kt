package com.homo_sapiens.ecosync.data.state.events

import com.homo_sapiens.ecosync.data.model.create.Event
import com.homo_sapiens.ecosync.data.model.event_detail.Comment
import com.homo_sapiens.ecosync.util.Settings

data class EventDetailState(
    val event: Event = Event(),
    val comments: List<Comment> = emptyList(),
    val userComment: Comment = Comment(
        id = Settings.currentUser.userId,
        user = Settings.currentUser.toPostUser()
    ),
    val isLoading: Boolean = true,
    val isReviewsLoading: Boolean = false,
    val date: RemainingDate = RemainingDate()
) {
    val showTimer = isLoading.not() && date.isZeros().not()
}

data class RemainingDate(
    val hour: List<Int> = emptyList(),
    val minute: List<Int> = emptyList(),
    val second: List<Int> = emptyList()
) {
    fun isZeros() = hour.all { it == 0 } && minute.all { it == 0 } && second.all { it == 0 }
}