package com.homo_sapiens.ecosync.data.state.auth

import com.homo_sapiens.ecosync.core.data.model.ErrorState
import com.homo_sapiens.ecosync.util.TextFieldState

data class SignupState(
    val email: TextFieldState = TextFieldState(),
    val fullName: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isFacultyPopupActive: Boolean = false,
    val dialogState: ErrorState? = null
)
