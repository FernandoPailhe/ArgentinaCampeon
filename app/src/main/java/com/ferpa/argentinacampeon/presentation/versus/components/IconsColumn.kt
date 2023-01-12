package com.ferpa.argentinacampeon.presentation.versus.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import com.ferpa.argentinacampeon.domain.model.PhotographerTitle


@Composable
fun IconsColumn(
    modifier: Modifier,
    isFavorite: Boolean,
    onStarClick: () -> Unit = {},
    onSendClick: () -> Unit = {},
    onCameraClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val isFavoriteOnChange = remember { mutableStateOf(isFavorite) }
        Icon(
            imageVector = if (isFavoriteOnChange.value) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
            tint = Color.White,
            contentDescription = "favorite",
            modifier = Modifier.clickable {
                onStarClick()
                isFavoriteOnChange.value = !isFavoriteOnChange.value
            }
        )
        Icon(
            imageVector = Icons.Default.Send,
            tint = Color.White,
            contentDescription = "send",
            modifier = Modifier.clickable {
                onSendClick()
            }
        )
    }
}