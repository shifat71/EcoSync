package com.homo_sapiens.ecosync.data.state.profile

import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.data.model.auth.User

data class ProfileState(
    val userId: String = "",
    val user: User? = null,
    val isLoading: Boolean = false
) {
    val isSelf = userId == Settings.currentUser.userId
}
