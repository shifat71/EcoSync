package com.homo_sapiens.ecosync.feature.announcement.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.homo_sapiens.ecosync.R
import com.homo_sapiens.ecosync.core.component.DefaultLinkifyText
import com.homo_sapiens.ecosync.data.model.announcement.Announcement

@Composable
fun AnnouncementPopup(
    announcement: Announcement,
    onDismissRequest: () -> Unit = { }
) {
    val uriHandler = LocalUriHandler.current

    Dialog(
        onDismissRequest = onDismissRequest,
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background, shape = RoundedCornerShape(5.dp))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = announcement.sender,
                            style = MaterialTheme.typography.h1.copy(
                                MaterialTheme.colors.onSurface,
                                fontSize = 12.sp
                            )
                        )

                        Text(
                            text = announcement.date,
                            style = MaterialTheme.typography.h1.copy(
                                MaterialTheme.colors.onSurface,
                                fontSize = 12.sp
                            )
                        )
                    }
                }

                item {
                    Text(
                        text = announcement.title,
                        style = MaterialTheme.typography.h1.copy(
                            MaterialTheme.colors.onBackground,
                            fontSize = 16.sp
                        )
                    )
                }

                item {
                    DefaultLinkifyText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textToSet = announcement.content
                    )
                }

                if (true) {
                    item {
                        Button(
                            onClick = { uriHandler.openUri(announcement.attachment) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.open_attach),
                                style = MaterialTheme.typography.h1.copy(
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}