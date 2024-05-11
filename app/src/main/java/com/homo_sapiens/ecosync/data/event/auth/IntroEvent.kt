package com.homo_sapiens.ecosync.data.event.auth

sealed class IntroEvent {
    object OnRegister : IntroEvent()
    object OnLogin : IntroEvent()
}