package com.homo_sapiens.ecosync.data.state.auth

import com.homo_sapiens.ecosync.core.data.model.ErrorState

data class IntroState(
    val isGoogleLoading: Boolean = false,
    val errorDialogState: ErrorState? = null
)
