package com.homo_sapiens.ecosync.data.model.announcement

import java.text.SimpleDateFormat
import java.util.*

data class Announcement(
    val title: String = "",
    val date: String = "",
    val sender: String = "",
    val activeDateRange: String = "",
    val content: String = "",
    val attachment: String = "",
    val baseUrl: String = ""
)

enum class AttachType {
    LINK,
    EMBED
}
