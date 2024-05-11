package com.homo_sapiens.ecosync.data.state.explore

import com.homo_sapiens.ecosync.data.model.share.Post

data class PostsState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasNext: Boolean = true
) {
    val showInitialLoading = isLoading && posts.isEmpty()
    val showEmptyScreen = hasNext.not() && posts.isEmpty() && isLoading.not()
}
