package com.ferpa.argentinacampeon.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.WELCOME_DIALOG_OFFSET

@Composable
fun WelcomeBackdrop(drawableResource: Int, offset: Float, page: Int, slideOut: Boolean = false) {
    val scrollOffset = (offset * WELCOME_DIALOG_OFFSET).dp
    val pageOffSet = (page * WELCOME_DIALOG_OFFSET).dp
    val slideOutOffset = if (slideOut) 350.dp else 0.dp
    // TODO Zoom in effect in the middle of the photo
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .offset(WELCOME_DIALOG_OFFSET.dp - scrollOffset - pageOffSet - slideOutOffset)
                .graphicsLayer(
                    scaleX = 2.5f,
                    scaleY = 2.5f,
                ),
            contentAlignment = Alignment.BottomCenter,
            content = {
                Image(
                    painter = painterResource(drawableResource),
                    contentDescription = "backdrop image",
                    modifier = Modifier
                        .fillMaxHeight(0.35f)
                        .offset(y = 90.dp),
                    contentScale = ContentScale.Crop
                )
            })
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
            content = {
                BottomGradient(0.5f, Constants.VioletDark)
            })
    }
}