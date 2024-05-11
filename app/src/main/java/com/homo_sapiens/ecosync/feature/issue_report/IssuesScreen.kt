package com.homo_sapiens.ecosync.feature.issue_report

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.core.component.cropper.CropperView
import com.homo_sapiens.ecosync.util.Screen
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.enterTransition
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.exitTransition
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.popEnterTransition
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.popExitTransition
import com.homo_sapiens.ecosync.data.event.share.ShareEvent
import com.homo_sapiens.ecosync.feature.auth.login.component.LoginButton
import com.homo_sapiens.ecosync.feature.share.component.ShareContentField
import com.homo_sapiens.ecosync.feature.share.component.SharePhotoSection
import com.homo_sapiens.ecosync.feature.share.component.ShareToolbar
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.homo_sapiens.ecosync.data.event.share.IssueEvent
import com.homo_sapiens.ecosync.feature.share.component.IssueContentField
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
fun IssuesScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: IssuesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            IssueScreenToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = {
                    clearBackStack()
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    IssuePhotoSection(
                        coverImage = state.postImage,
                        onShareEvent = viewModel::onEvent
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    IssueContentField(text = state.bodyState.text) {
                        viewModel.onEvent(IssueEvent.OnBody(it))
                    }
                }
                item {

                         TickComposable(isChecked = state.isAnonymous){
                             viewModel.onEvent(IssueEvent.IsAnonymous(it))
                         }

                }
            }
        }
        LoginButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(44.dp)
                .align(Alignment.BottomCenter),
            clicked = state.isLoading,
            text = stringResource(id = R.string.issue_submit)
        ) {
            viewModel.submit()
        }
    }
    AnimatedVisibility(visible = state.selectedImage.isNotEmpty(),
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        CropperView(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
            uri = Uri.parse(state.selectedImage),
            onCropEvent = viewModel::onCropperEvent)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.issuesComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit
) {
    composable(
        route = Screen.IssuesScreen.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        IssuesScreen(onNavigate, clearBackStack)
    }
}

@Composable
fun TickComposable(
    isChecked: Boolean,
    isTicked: (Boolean) -> Unit
    ) {
    if (isChecked) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Checked",
            tint = MaterialTheme.colors.primary
        )
        isTicked(true)
    } else {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Not Checked",
            tint = MaterialTheme.colors.error
        )
        isTicked(false)
    }
}

