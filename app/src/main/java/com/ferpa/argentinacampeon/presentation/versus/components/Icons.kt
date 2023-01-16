package com.ferpa.argentinacampeon.presentation.versus.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.ICON_SIZE
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing


@Composable
fun Icons(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onBookmarkClick: () -> Unit = {},
    onSendClick: () -> Unit = {},
    isPhotoCard: Boolean = false
) {
    if (isPhotoCard) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val isFavoriteOnChange = remember { mutableStateOf(isFavorite) }
            Icon(
                imageVector = if (isFavoriteOnChange.value) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
                tint = Color.White,
                contentDescription = "favorite",
                modifier = Modifier
                    .clickable {
                        onBookmarkClick()
                        isFavoriteOnChange.value = !isFavoriteOnChange.value
                    }
                    .padding(MaterialTheme.spacing.small)
                    .size(ICON_SIZE.dp)
            )
        }
    } else {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val isFavoriteOnChange = remember { mutableStateOf(isFavorite) }
            Box() {
                Icon(
                    imageVector = if (isFavoriteOnChange.value) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
                    tint = Constants.VioletTransparent,
                    contentDescription = "favorite",
                    modifier = Modifier
                        .size(ICON_SIZE.dp)
                        .offset(1.dp, 1.dp)
                        .blur(2.dp),
                )
                Icon(
                    imageVector = if (isFavoriteOnChange.value) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
                    tint = Color.White,
                    contentDescription = "favorite",
                    modifier = Modifier
                        .clickable {
                            onBookmarkClick()
                            isFavoriteOnChange.value = !isFavoriteOnChange.value
                        }
                        .size(ICON_SIZE.dp),
                )
            }
            Box() {
                Icon(
                    imageVector = Icons.Default.Send,
                    tint = Constants.VioletTransparent,
                    contentDescription = "send",
                    modifier = Modifier
                        .size(ICON_SIZE.dp)
                        .offset(1.dp, 1.dp)
                        .blur(2.dp)
                )
                Icon(
                    imageVector = Icons.Default.Send,
                    tint = Color.White,
                    contentDescription = "send",
                    modifier = Modifier
                        .clickable {
                            onSendClick()
                        }
                        .size(ICON_SIZE.dp)
                )
            }
        }
    }
}