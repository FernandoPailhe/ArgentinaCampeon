package com.ferpa.argentinacampeon.presentation.photo_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing

@Composable
fun StatsText(
    stat: String,
    number: Int?,
    isRow: Boolean = true
) {
    if (number == null) return
    if (isRow) {
        val percent = if (stat == "Precisión de pases") "%" else ""
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
            text = "$stat: $number$percent",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    } else {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.small
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stat, color = Color.White, textAlign = TextAlign.Center)
            Text(
                text = number.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }


}