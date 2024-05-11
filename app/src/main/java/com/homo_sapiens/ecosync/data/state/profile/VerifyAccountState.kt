package com.homo_sapiens.ecosync.data.state.profile

import com.homo_sapiens.ecosync.util.TextFieldState

data class VerifyAccountState(
    val isLoading: Boolean = false,
    val verifyCode: TextFieldState = TextFieldState(),
) {
    val canVerify = verifyCode.text.length == 6
}
