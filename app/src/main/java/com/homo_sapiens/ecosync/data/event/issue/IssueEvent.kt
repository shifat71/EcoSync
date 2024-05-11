package com.homo_sapiens.ecosync.data.event.share

import android.net.Uri

sealed class IssueEvent {
    data class OnImage(val uri: Uri): IssueEvent()
    data class OnBody(val text: String): IssueEvent()
    data class OnLocation(val text: String): IssueEvent()
    data class IsAnonymous(val mark:Boolean) : IssueEvent()
}
