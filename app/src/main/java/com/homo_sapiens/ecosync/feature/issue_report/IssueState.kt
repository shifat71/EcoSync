package com.homo_sapiens.ecosync.feature.issue_report

import com.homo_sapiens.ecosync.util.TextFieldState
import com.homo_sapiens.ecosync.util.isTicked

data class IssueState(
    val isLoading: Boolean = false,
    val selectedImage: String = "",
    val postImage: String = "",
    val bodyState: TextFieldState = TextFieldState(),
    val location: TextFieldState = TextFieldState(),
    val isAnonymous: Boolean = false,
    val isResolved: Boolean = false
)
