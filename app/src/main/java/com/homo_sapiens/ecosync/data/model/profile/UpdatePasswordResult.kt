package com.homo_sapiens.ecosync.data.model.profile

data class UpdatePasswordResult(
    val emailError: String = "",
    val passwordError: String = "",
    val otherError: String = "",
    val isSuccess: Boolean = true
)
