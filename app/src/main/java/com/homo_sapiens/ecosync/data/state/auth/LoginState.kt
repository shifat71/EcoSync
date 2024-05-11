package com.homo_sapiens.ecosync.data.state.auth

import com.homo_sapiens.ecosync.core.data.model.ErrorState
import com.homo_sapiens.ecosync.util.TextFieldState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val dialogState: ErrorState? = null
)
