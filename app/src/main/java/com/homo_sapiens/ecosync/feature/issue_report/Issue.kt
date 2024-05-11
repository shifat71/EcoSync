package com.homo_sapiens.ecosync.feature.issue_report

import com.homo_sapiens.ecosync.util.extension.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Issue(
    val id: String = "",
    val body: String = "",
    val image: String = "",
    val type: String ="",
    val location: String,
    val userId: String="",
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val isResolved: Boolean = false,
    val isAnonymous: Boolean = false
) {
    val formattedDate: String = createdAt.toDate().formatDate()
}

data class IssueDto(
    @DocumentId
    val id: String = "",
    val body: String = "",
    val image: String = "",
    val type: String ="",
    val location: String="",
    val userId: String="",
    val createdAt: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    val isResolved: Boolean = false,
    val isAnonymous: Boolean = false
) {
    fun toIssue() = Issue(
        id = id,
        body = body,
        image = image,
        type = type,
        userId = userId,
        isResolved = isResolved,
        isAnonymous = isAnonymous,
        createdAt = createdAt,
        location = location,
    )
}
