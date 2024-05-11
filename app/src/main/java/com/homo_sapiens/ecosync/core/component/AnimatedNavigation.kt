package com.homo_sapiens.ecosync.core.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import com.homo_sapiens.ecosync.util.Screen
import com.homo_sapiens.ecosync.feature.auth.introduction.introComposable
import com.homo_sapiens.ecosync.feature.auth.login.loginComposable
import com.homo_sapiens.ecosync.feature.auth.signup.signupComposable
import com.homo_sapiens.ecosync.feature.auth.splash.splashComposable
import com.homo_sapiens.ecosync.feature.polls.pollsComposable
import com.homo_sapiens.ecosync.feature.create.createComposable
import com.homo_sapiens.ecosync.feature.explore.exploreComposable
import com.homo_sapiens.ecosync.feature.announcement.announcementComposable
import com.homo_sapiens.ecosync.feature.auth.signup.verify_email.verifyEmailComposable
import com.homo_sapiens.ecosync.feature.events.detail.eventDetailComposable
import com.homo_sapiens.ecosync.feature.events.list.eventsComposable
import com.homo_sapiens.ecosync.feature.image_detail.imageDetailComposable
import com.homo_sapiens.ecosync.feature.profile.settings.edit_profile.editProfileComposable
import com.homo_sapiens.ecosync.feature.polls.create_poll.createPollComposable
import com.homo_sapiens.ecosync.feature.profile.attended_events.profileAttendedEventsComposable
import com.homo_sapiens.ecosync.feature.profile.liked_events.profileLikedEventsComposable
import com.homo_sapiens.ecosync.feature.profile.polls.profilePollsComposable
import com.homo_sapiens.ecosync.feature.profile.posts.profilePostsComposable
import com.homo_sapiens.ecosync.feature.profile.profile.profileComposable
import com.homo_sapiens.ecosync.feature.profile.settings.delete_account.deleteAccountComposable
import com.homo_sapiens.ecosync.feature.profile.settings.settings.settingsComposable
import com.homo_sapiens.ecosync.feature.profile.settings.update_password.updatePasswordComposable
import com.homo_sapiens.ecosync.feature.profile.settings.verify.verifyAccountComposable
import com.homo_sapiens.ecosync.feature.share.shareComposable
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.homo_sapiens.ecosync.feature.issue_report.issuesComposable

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun BaseAnimatedNavigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    val navigateSingleTop: (String) -> Unit = {
        navController.navigate(it) {
            launchSingleTop = true
        }
    }
    val navigateClearWholeBackstack: (String) -> Unit = {
        navController.navigate(it) {
            popUpTo(0)
        }
    }
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    AnimatedNavHost(
        modifier = Modifier.navigationBarsWithImePadding(),
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        splashComposable(navigateClearWholeBackstack, navController::popBackStack)
        introComposable(navigateSingleTop, navigateClearWholeBackstack, navController::popBackStack)
        loginComposable(navigateClearWholeBackstack, navController::popBackStack)
        signupComposable(navigateClearWholeBackstack, navController::popBackStack, scaffoldState)
        verifyEmailComposable(navigateClearWholeBackstack, navController::popBackStack, scaffoldState)
        exploreComposable(navigateSingleTop, navController::popBackStack)
        eventsComposable(navigateSingleTop, navController::popBackStack, viewModelStoreOwner)
        eventDetailComposable(navigateSingleTop, navController::popBackStack)
        pollsComposable(navigateSingleTop, navController::popBackStack, viewModelStoreOwner)
        createPollComposable(navigateSingleTop, navController::popBackStack)
        announcementComposable(navigateSingleTop, navController::popBackStack, viewModelStoreOwner)
        issuesComposable(navigateSingleTop, navController::popBackStack)
        createComposable(navigateSingleTop, navController::popBackStack, scaffoldState)
        shareComposable(navigateSingleTop, navController::popBackStack)
        settingsComposable(navigateSingleTop, navController::popBackStack)
        editProfileComposable(navigateSingleTop, navController::popBackStack)
        profileComposable(navigateSingleTop, navController::popBackStack)
        profilePollsComposable(navigateSingleTop, navController::popBackStack)
        profileLikedEventsComposable(navigateSingleTop, navController::popBackStack)
        profileAttendedEventsComposable(navigateSingleTop, navController::popBackStack)
        profilePostsComposable(navigateSingleTop, navController::popBackStack)
        updatePasswordComposable(navigateSingleTop, navController::popBackStack)
        deleteAccountComposable(navigateSingleTop, navController::popBackStack)
        verifyAccountComposable(navigateSingleTop, navController::popBackStack)
        imageDetailComposable(navController::popBackStack)
    }
}