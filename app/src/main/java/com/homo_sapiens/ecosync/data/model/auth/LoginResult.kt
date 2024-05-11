package com.homo_sapiens.ecosync.data.model.auth

data class LoginResult(
    val emailError: String = "",
    val passwordError: String = "",
    val isSuccess: Boolean = false
)
