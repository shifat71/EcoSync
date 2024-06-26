package com.homo_sapiens.ecosync.data.model.create

import com.homo_sapiens.ecosync.data.model.auth.PostUser
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.util.extension.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Event(
    val id: String = "",
    val user: String ="",
    val attendee: List<PostUser> = emptyList(),
    val attendeePlusCount: Int = 0,
    val totalAttendeeCount: Int = 0,
    val likedBy: List<String> = emptyList(),
    val isLiked: Boolean = false,
    val isAttended: Boolean = false,
    val likedByPlusCount: Int = 0,
    val title: String = "",
    val description: String = "",
    val fullDate: String = "",
    val startDate: String = "",
    val location: String = "",
    val coverImage: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val month: String = "",
    val day: String = "",
    val startTime: Date = Date()
) {
    val showIndicator = totalAttendeeCount > 0 && likedByPlusCount > 0
    val dayMonth = "$month\n$day"

    val showAttendeeCount = totalAttendeeCount > 0
    val showUserHeads = attendee.isNotEmpty() && showAttendeeCount
    val isStarted = Date(System.currentTimeMillis()) >= startTime
}

data class EventDto(

    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startTime: Timestamp = Timestamp.now(),
    val endTime: Timestamp = Timestamp.now(),
    val location: String = "",
    val coverImage: String = "",
    val likedBy: List<String> = emptyList(),
    val attendee: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now(),
    val user: String = ""
) {
    /*private val totalAttendeeC = attendee.count()
    private val takenAttendee = attendee.shuffled().take(5)
    private val plusAttendeeCount =
        (totalAttendeeC - takenAttendee.count()).takeIf { it >= 0 } ?: 0*/

    fun toEvent() = Event(
        id = id,
        user = user,
        title = title,
        description = description,
        location = location,
        coverImage = coverImage,
        attendee = attendee.shuffled().take(5).map { m ->
            Settings.userStorage.firstOrNull { it.userId == m } ?: PostUser()
        },
        attendeePlusCount = (attendee.count() - minOf(5, attendee.count())).takeIf { it >= 0 } ?: 0,
        totalAttendeeCount = attendee.count(),
        fullDate = "${
            startTime.toDate().formatDate("EEEE, MMM dd, yyyy HH:mm")
        } - ${endTime.toDate().formatDate("EEEE, MMM dd, yyyy HH:mm")}",
        startDate = startTime.toDate().formatDate("EE, MMM dd, yyyy HH:mm").uppercase(),
        month = startTime.toDate().formatDate("MMM").uppercase(),
        day = startTime.toDate().formatDate("dd"),
        createdAt = createdAt,
        likedBy = likedBy,
        isLiked = Settings.currentUser.userId in likedBy,
        isAttended = Settings.currentUser.userId in attendee,
        likedByPlusCount = likedBy.count(),
        startTime = startTime.toDate()
    )
}
