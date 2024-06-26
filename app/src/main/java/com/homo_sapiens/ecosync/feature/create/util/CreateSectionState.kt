package com.homo_sapiens.ecosync.feature.create.util

import android.net.Uri
import com.homo_sapiens.ecosync.util.TextFieldState
import com.homo_sapiens.ecosync.data.model.create.EventDto
import com.homo_sapiens.ecosync.util.Settings
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class CreateSectionState(
    val title: TextFieldState = TextFieldState(),
    val description: TextFieldState = TextFieldState(),
    val location: TextFieldState = TextFieldState(),
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val coverImage: Uri? = null,
    val cropperImage: String = ""
) {
    val formattedStartDate: String = startDate.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )
    val formattedStartHour: String = startTime.format(
        DateTimeFormatter.ofPattern("HH:mm")
    )
    val formattedEndDate: String = endDate.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )
    val formattedEndHour: String = endTime.format(
        DateTimeFormatter.ofPattern("HH:mm")
    )
    val startTimeLong: Long = LocalDateTime.of(startDate, startTime)
        .toEpochSecond(ZoneOffset.UTC)
    val endTimeLong: Long = LocalDateTime.of(endDate, endTime)
        .toEpochSecond(ZoneOffset.UTC)

    fun toCreateEventModel(): EventDto {
        return EventDto(
            title = title.text,
            description = description.text,
            startTime = Timestamp(startTimeLong - 60 * 60 * 3, 1000),
            endTime = Timestamp(endTimeLong - 60 * 60 * 3, 1000),
            location = location.text,
            coverImage = coverImage?.toString() ?: "",
            user = Settings.currentUser.userId
        )
    }
}