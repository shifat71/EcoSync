package com.homo_sapiens.ecosync.data.event.auth

sealed class VerifyEmailEvent {
    object OnContinue : VerifyEmailEvent()
    object OnResend : VerifyEmailEvent()
}