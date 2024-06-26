package com.homo_sapiens.ecosync.feature.events.detail.lazy_items.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.homo_sapiens.ecosync.data.event.auth.event_detail.EventDetailEvent
import com.homo_sapiens.ecosync.data.model.event_detail.EventDetailPage
import com.homo_sapiens.ecosync.data.state.events.EventDetailState
import com.homo_sapiens.ecosync.util.modifier.pagerTabIndicatorOffset
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalPagerApi::class
)
fun LazyListScope.tabSection(
    state: EventDetailState,
    onEvent: (EventDetailEvent) -> Unit,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    pages: List<EventDetailPage>
) {
    val event = state.event
    stickyHeader {
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = MaterialTheme.colors.primary
                )
            }
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                val textColorAnim =
                    animateColorAsState(
                        targetValue = if (index == pagerState.currentPage) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSecondary
                    ).value
                Tab(
                    text = {
                        Text(
                            stringResource(id = title.title),
                            style = MaterialTheme.typography.h4.copy(
                                color = textColorAnim,
                                fontSize = 18.sp
                            )
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
    }

    item {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { page ->
            when (page) {
                0 -> DetailPage(
                    desc = event.description,
                    showContent = pagerState.currentPage == 0
                )
                1 -> CommentsPage(
                    comment = state.userComment,
                    comments = state.comments,
                    onEvent = onEvent,
                    showContent = pagerState.currentPage == 1
                )
            }
        }
    }
}