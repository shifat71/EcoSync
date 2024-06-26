package com.homo_sapiens.ecosync.feature.auth.login.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.core.ui.theme.Shapes
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.with_google),
    icon: Int = R.drawable.ic_google_logo,
    shape: Shape = Shapes.medium,
    borderColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.surface,
    clicked: Boolean = false,
    enabled: Boolean = clicked.not(),
    onClicked: () -> Unit
) {
    var rotation by remember { mutableStateOf(360F) }
    val rotationState by animateFloatAsState(
        targetValue = if (clicked) rotation else 0F,
        animationSpec = tween(
            durationMillis = 1000,
        )
    )
    LaunchedEffect(key1 = rotation, key2 = clicked) {
        delay(1000L)
        if (clicked) rotation += 360F
    }

    Surface(
        onClick = {
            if (enabled) {
                onClicked()
                rotation = 360F
            }
        },
        modifier = modifier,
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.rotate(rotationState),
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
            )
            AnimatedVisibility(
                visible = clicked.not(), enter = expandHorizontally(
                    animationSpec = tween(durationMillis = 500)
                ), exit = shrinkHorizontally(
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                Row {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = text, style = MaterialTheme.typography.h2)
                }
            }
        }
    }
}