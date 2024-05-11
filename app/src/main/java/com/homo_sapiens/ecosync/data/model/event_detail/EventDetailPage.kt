package com.homo_sapiens.ecosync.data.model.event_detail

import androidx.annotation.StringRes
import com.homo_sapiens.ecosync.R

enum class EventDetailPage(@StringRes val title: Int) {
    DETAIL(R.string.event_detail_detail),
    COMMENTS(R.string.event_detail_comments)
}

val pages = listOf(
    EventDetailPage.DETAIL,
    EventDetailPage.COMMENTS
)