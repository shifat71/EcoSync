package com.homo_sapiens.ecosync.data.event.auth.event_detail

sealed class EventDetailEvent {
    data class OnRating(val rating: Float) : EventDetailEvent()
    data class OnComment(val comment: String) : EventDetailEvent()
    object OnReview : EventDetailEvent()
}