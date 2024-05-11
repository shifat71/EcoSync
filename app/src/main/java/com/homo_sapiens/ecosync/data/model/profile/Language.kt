package com.homo_sapiens.ecosync.data.model.profile

enum class Language(val value: String = "", val key: String = "") {
    ENGLISH("English", "en"),
    JAPANESE("Japanese", "tr"),
    UNDEFINED
}

fun Int.toLanguage() = when (this) {
    0 -> Language.ENGLISH
    1 -> Language.JAPANESE
    else -> Language.UNDEFINED
}

val selectableLanguages = listOf(
    Language.ENGLISH,
    Language.JAPANESE
)