package com.homo_sapiens.ecosync.util

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val route: String = "",
    @DrawableRes val icon: Int? = null,
    val contentDescription: String = ""
)
