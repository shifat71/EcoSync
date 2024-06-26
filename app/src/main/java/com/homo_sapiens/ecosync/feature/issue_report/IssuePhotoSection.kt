package com.homo_sapiens.ecosync.feature.issue_report

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.data.event.share.IssueEvent
import com.homo_sapiens.ecosync.data.event.share.ShareEvent

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun IssuePhotoSection(
    coverImage: String,
    onShareEvent: (IssueEvent) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null) onShareEvent(IssueEvent.OnImage(it))
    }

    if (coverImage.isNotEmpty()) {
        SelectedImageView(coverImage) {
            launcher.launch("image/*")
        }
    } else {
        SelectImageView {
            launcher.launch("image/*")
        }
    }
}


@Composable
private fun SelectedImageView(coverImage: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = coverImage)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        size(Size.ORIGINAL)
                    }).build()
            ),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
    }
}

@Composable
private fun SelectImageView(onClick: () -> Unit) {
    val stroke = Stroke(
        width = 4F,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20F, 25F), 0f)
    )
    val primaryColor = MaterialTheme.colors.primary
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
            .height(170.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = primaryColor,
                style = stroke
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_select_photo),
                contentDescription = stringResource(
                    id = R.string.select_image
                ),
                tint = primaryColor
            )
            Text(
                text = stringResource(id = R.string.select_post_photo),
                style = MaterialTheme.typography.h1
            )
        }
    }
}