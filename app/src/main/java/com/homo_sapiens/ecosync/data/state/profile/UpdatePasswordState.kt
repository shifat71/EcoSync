package com.homo_sapiens.ecosync.data.state.profile

import com.homo_sapiens.ecosync.core.data.model.ErrorState
import com.homo_sapiens.ecosync.util.TextFieldState

data class UpdatePasswordState(
    val isLoading: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val newPassword: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val errorDialogState: ErrorState? = null
)
