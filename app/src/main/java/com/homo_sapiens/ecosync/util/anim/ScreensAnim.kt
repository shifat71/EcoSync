package com.homo_sapiens.ecosync.util.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.homo_sapiens.ecosync.util.Screen

@ExperimentalAnimationApi
object ScreensAnim {
    private val BOTTOM_NAV_ITEMS = listOf(
        Screen.Home.route,
        Screen.Events.route,
        Screen.Meal.route,
        Screen.Announcement.route,
        Screen.Polls.route
    )

    fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition {
        return when (targetState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeIn()
            Screen.CreateEvent.route, Screen.Share.route, Screen.CreatePoll.route -> slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { it })
            else -> slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it })
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return when (targetState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeOut()
            Screen.CreateEvent.route, Screen.Share.route, Screen.CreatePoll.route -> slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { (-it / 7) })
            else -> slideOutHorizontally(
                animationSpec = tween(300),
                targetOffsetX = { (-it / 7) })
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
        return when (initialState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeIn()
            Screen.CreateEvent.route, Screen.Share.route, Screen.CreatePoll.route -> slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { -it })
            else -> slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { -it })
        }
    }

    fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return when (initialState.destination.route) {
            in BOTTOM_NAV_ITEMS -> fadeOut()
            Screen.CreateEvent.route, Screen.Share.route, Screen.CreatePoll.route -> slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { it / 7 })
            else -> slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it / 7 })
        }
    }

    fun fadeIn(): EnterTransition {
        return fadeIn(animationSpec = tween(300))
    }

    fun fadeOut(): ExitTransition {
        return fadeOut(animationSpec = tween(300))
    }
}