package com.homo_sapiens.ecosync.feature.profile.settings.edit_profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.core.component.cropper.CropperView
import com.homo_sapiens.ecosync.core.presentation.MainActivity
import com.homo_sapiens.ecosync.util.Screen
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.enterTransition
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.exitTransition
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.popEnterTransition
import com.homo_sapiens.ecosync.util.anim.ScreensAnim.popExitTransition
import com.homo_sapiens.ecosync.feature.auth.login.component.LoginButton
import com.homo_sapiens.ecosync.feature.profile.settings.edit_profile.component.*
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.homo_sapiens.ecosync.feature.profile.settings.settings.component.sectionTitle
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun SettingsScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val accountTitle = stringResource(id = R.string.personal)
    val socialTitle = stringResource(id = R.string.social_accounts)

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.RestartApp -> {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                }
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            EditProfileToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = clearBackStack
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                contentPadding = PaddingValues(top = 20.dp, bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                sectionTitle(
                    accountTitle, modifier = Modifier.padding(
                        horizontal = 32.dp, vertical = 8.dp
                    )
                )
                photoSection(state.profilePic, viewModel::onEvent)
                nameSection(state.fullName, viewModel::onEvent)

                sectionTitle(
                    socialTitle, modifier = Modifier.padding(
                        horizontal = 32.dp, vertical = 8.dp
                    )
                )
                socialSection(
                    instagram = state.instagram,
                    twitter = state.twitter,
                    linkedIn = state.linkedIn,
                    github = state.github,
                    event = viewModel::onEvent
                )
            }
        }

        LoginButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(44.dp)
                .align(Alignment.BottomCenter),
            clicked = state.isLoading,
            text = stringResource(id = R.string.update_profile)
        ) {
            viewModel.updateProfile()
        }
    }

    AnimatedVisibility(visible = state.cropperImage.isNotEmpty(),
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        CropperView(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
            uri = Uri.parse(state.cropperImage),
            ratio = listOf(1, 1),
            onCropEvent = viewModel::onCropperEvent)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.editProfileComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit
) {
    composable(
        route = Screen.EditProfile.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        SettingsScreen(onNavigate, clearBackStack)
    }
}