package com.ferpa.argentinacampeon.presentation.photo_detail.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.getUrlIconFromUrl
import com.ferpa.argentinacampeon.common.Extensions.linkIntent
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Hyperlink(
    link: String,
    withIcon: Boolean = false,
    onlyIcon: Boolean = false,
    onClick: () -> Unit = {}
) {
    if (withIcon && onlyIcon) {
        GlideImage(
            model = link.getUrlIconFromUrl(),
            contentDescription = link,
            modifier = Modifier
                .size(Constants.ICON_SIZE.dp)
                .clickable {
                    onClick()
                },
            contentScale = ContentScale.Fit,
        )
    } else {
        Text(
            text = link,
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium)
                .clickable {
                    onClick()
                },
            textDecoration = TextDecoration.Underline,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}