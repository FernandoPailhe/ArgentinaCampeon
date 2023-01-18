package com.ferpa.argentinacampeon.presentation.photo_list.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.drawable.DrawableResource
import com.ferpa.argentinacampeon.common.Constants

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TopGradientBackdrop(drawableResource: Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        GlideImage(
            model = drawableResource,
            contentDescription = "player",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
        )
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
        ) {
            val aspectRatio = maxWidth / maxHeight
            Box(
                Modifier
                    .height((Constants.TOP_BACKGROUND_HEIGHT / 3).dp)
                    .fillMaxWidth()
                    .scale(maxOf(aspectRatio, 1f), maxOf(1 / aspectRatio, 1f))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xA218182F), Color(0xFFFFFF))
                        ),
                    )
            )
        }
    }
}
