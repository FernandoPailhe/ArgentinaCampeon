package com.ferpa.argentinacampeon.presentation.photo_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing

@Composable
fun ResultColumn(
    team: String?,
    teamScore: String?,
    scores: List<String?>?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        team?.apply {
            Text(
                text = this,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default),
                overflow = TextOverflow.Ellipsis,
            )
        }
        teamScore?.apply {
            Text(
                text = this,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default),
                overflow = TextOverflow.Ellipsis,
            )
        }
        scores?.apply {
            this.filterNotNull().forEach { goal ->
                Text(
                    text = goal,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.default),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

}