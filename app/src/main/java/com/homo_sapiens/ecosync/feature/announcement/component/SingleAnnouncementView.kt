package com.homo_sapiens.ecosync.feature.announcement.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.data.model.announcement.Announcement

@Composable
fun SingleAnnouncementView(
    announcement: Announcement,
    modifier: Modifier = Modifier,
    onClick: (Announcement) -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = { onClick(announcement) })
            .padding(24.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = announcement.sender,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.weight(1F))
            if (announcement.attachment.isNotBlank()) Icon(
                painter = painterResource(id = R.drawable.ic_attachement),
                contentDescription = null, modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp), tint = MaterialTheme.colors.primary
            )
            Text(
                text = announcement.date,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                )
            )
        }
        if (announcement.title.isNotEmpty())
            Text(
                text = announcement.title,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onBackground,
                    fontSize = 18.sp
                ), maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        if (announcement.content.isNotEmpty())
            Text(
                text = announcement.content,
                style = MaterialTheme.typography.h1.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                ), maxLines = 4, overflow = TextOverflow.Ellipsis
            )
    }
}