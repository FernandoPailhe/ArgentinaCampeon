package com.ferpa.argentinacampeon.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.ferpa.argentinacampeon.common.Constants

private val DarkColorPalette = darkColors(
    primary = Constants.Violet,
    primaryVariant = Constants.VioletDark,
    secondary = Constants.LightBlue
)

private val LightColorPalette = lightColors(
    primary = Constants.Violet,
    primaryVariant = Constants.VioletDark,
    secondary = Constants.LightBlue

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun BestQatar2022PhotosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}