package com.homo_sapiens.ecosync.feature.issue_report


data class IssuesState(
    val issues: List<Issue> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasNext: Boolean = true
) {
    val showInitialLoading = isLoading && issues.isEmpty()
    val showEmptyScreen = hasNext.not() && issues.isEmpty() && isLoading.not()
}
