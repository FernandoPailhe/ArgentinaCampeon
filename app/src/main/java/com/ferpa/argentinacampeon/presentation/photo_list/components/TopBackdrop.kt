package com.ferpa.argentinacampeon.presentation.photo_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ferpa.argentinacampeon.common.Constants

@Composable
fun TopBackdrop(drawableResource: Int, height: Int = Constants.TOP_BACKGROUND_HEIGHT) {
    Box(modifier = Modifier.fillMaxWidth().height(height.dp)) {
        Image(
            painter = painterResource(drawableResource),
            contentDescription = "backdrop image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
        ) {
            val aspectRatio = maxWidth / maxHeight
            Box(
                Modifier
                    .fillMaxSize()
                    .scale(maxOf(aspectRatio, 1f), maxOf(1 / aspectRatio, 1f))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFFF), Constants.VioletDark),
                        ),
                    )
            )
        }
    }
}