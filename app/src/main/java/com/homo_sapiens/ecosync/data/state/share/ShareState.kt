package com.homo_sapiens.ecosync.data.state.share

import com.homo_sapiens.ecosync.util.TextFieldState

data class ShareState(
    val isLoading: Boolean = false,
    val selectedImage: String = "",
    val postImage: String = "",
    val bodyState: TextFieldState = TextFieldState()
)
