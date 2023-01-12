package com.ferpa.argentinacampeon.presentation.photo_list.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.Match
import com.ferpa.argentinacampeon.domain.model.getMatchTitle
import com.ferpa.argentinacampeon.domain.model.getScore
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.R

@Composable
fun MatchInfoBox(
    modifier: Modifier = Modifier,
    match: Match?,
    scrollState: State<Float> = mutableStateOf(0.33f)
) {
    Log.d("MatchInfoBox", match?.getMatchTitle().toString())
    if (match == null) return
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Constants.VioletDark)
    ) {
        val drawableResource = when (match.stadiumId) {
            "Ahmed bin Ali" -> R.drawable.ahmed
            "Lusail" -> R.drawable.lusail
            "974" -> R.drawable.stadium_974
            else -> R.drawable.logo_qatar
        }
        TopBackdrop(drawableResource = drawableResource)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.spacing.default,
                    horizontal = MaterialTheme.spacing.medium
                ),
            contentAlignment = Alignment.TopCenter,
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = match.tournamentInstance ?: "",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.default)
                            .fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "${match.stadiumId} Stadium",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.default)
                            .fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.spacing.default),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ResultColumn(
                            match.teamA,
                            match.getScore().first,
                            match.scoreTeamA
                        )

                        ResultColumn(
                            match.teamB,
                            match.getScore().second,
                            match.scoreTeamB
                        )
                    }
                    match.description?.apply {
                        Text(
                            text = this,
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default)
                        )
                    }
                }
            }
        )
    }
}
