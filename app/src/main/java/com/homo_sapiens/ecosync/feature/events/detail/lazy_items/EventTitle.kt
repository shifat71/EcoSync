package com.homo_sapiens.ecosync.feature.events.detail.lazy_items

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.core.ui.theme.Red

fun LazyListScope.eventTitle(
    title: String,
    isLiked: Boolean,
    date: String,
    onLike: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 24.sp
                ), modifier = Modifier
                    .padding(end = 10.dp)
                    .weight(9F)
            )
            Spacer(modifier = Modifier.weight(1F))
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(10.dp))
                    .clickable(onClick = onLike),
                contentAlignment = Alignment.Center
            ) {
                val colorAnim =
                    animateColorAsState(targetValue = if (isLiked) Red else MaterialTheme.colors.onBackground).value
                val scaleAnim = animateFloatAsState(
                    targetValue = if (isLiked) 1.1F else 1F,
                    animationSpec = spring(Spring.DampingRatioHighBouncy, Spring.StiffnessLow)
                ).value

                Icon(
                    painter = painterResource(id = if (isLiked) R.drawable.ic_like_active else R.drawable.ic_like),
                    contentDescription = null,
                    tint = colorAnim,
                    modifier = Modifier
                        .scale(scaleAnim)
                )
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date, style = MaterialTheme.typography.h4.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onPrimary
                    ), textAlign = TextAlign.Center
                )
            }
        }
    }
}