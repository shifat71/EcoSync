package com.homo_sapiens.ecosync.data.state.poll

data class CreatePollState(
    val title: String = "",
    val options: List<String> = listOf("", ""),
    val isLoading: Boolean = false
)
