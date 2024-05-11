package com.homo_sapiens.ecosync.util

data class TextFieldState(
    val text: String = "",
    val error: String = "",
    val isPasswordShowing: Boolean = false
)

data class isTicked(
    val isTicked: Boolean = false,
    val error: String =""
)
