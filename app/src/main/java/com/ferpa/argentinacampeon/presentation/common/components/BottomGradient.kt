package com.ferpa.argentinacampeon.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.ferpa.argentinacampeon.presentation.ui.theme.AppColors.VioletUltraDark

@Composable
fun BottomGradient(maxHeight: Float = 0.03f, bottomColor: Color = VioletUltraDark) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(maxHeight)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(
                            0.0f,
                            Color.Transparent
                        ),
                        Pair(
                            0.7f,
                            bottomColor
                        )
                    )
                )
            )
    )
}

