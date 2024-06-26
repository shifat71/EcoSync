package com.homo_sapiens.ecosync.data.state.auth

data class VerifyEmailState(
    val isLoading: Boolean = false,
    val resendSecond: Int = 0
) {
    val showSecond = resendSecond > 0
}
