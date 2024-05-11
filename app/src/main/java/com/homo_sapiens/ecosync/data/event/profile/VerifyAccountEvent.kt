package com.homo_sapiens.ecosync.data.event.profile


sealed class VerifyAccountEvent {
    data class OnVerifyCode(val text: String): VerifyAccountEvent()
    object OnVerifyAccount: VerifyAccountEvent()
}