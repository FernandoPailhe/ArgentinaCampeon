@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.ferpa.argentinacampeon.presentation.photo_list.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.data.previewsource.PreviewPhotos
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerInfoBox(
    modifier: Modifier = Modifier,
    player: Player?,
    bestPhoto: String? = null,
    scrollState: State<Float> = mutableStateOf(0.33f)
) {
    if (player == null) return
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
            .background(Constants.VioletDark)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
        ) {
            bestPhoto.apply {
                GlideImage(
                    model = bestPhoto ?: painterResource(id = R.drawable.fot_bloqueada),
                    contentDescription = "player",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
                )
            }
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
            ) {
                val aspectRatio = maxWidth / maxHeight
                Box(
                    Modifier
                        .fillMaxHeight(0.6f)
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

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter,
            content = {
                BoxWithConstraints(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    val aspectRatio = maxWidth / maxHeight
                    Box(
                        Modifier
                            .fillMaxSize()
                            .scale(maxOf(aspectRatio, 1f), maxOf(1 / aspectRatio, 1f))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFFFFFF), Color(0xA218182F))
                                ),
                            )
                    )
                }
                player.stats?.apply {
                    val scrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState, true)
                            .padding(MaterialTheme.spacing.default),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        StatsText(stat = "Partidos", number = player.stats.matches)
                        StatsText(stat = "Minutos", number = player.stats.minutes)
                        StatsText(stat = "Goles", number = player.stats.goals)
                        StatsText(stat = "Asistencias", number = player.stats.assists)
                        StatsText(stat = "Disparos", number = player.stats.shots)
                        StatsText(stat = "Pases", number = player.stats.passes)
                        StatsText(
                            stat = "Precisión de pases",
                            number = player.stats.passAccuracy
                        )
                        StatsText(stat = "Recuperaciones", number = player.stats.recoveries)
                        StatsText(stat = "Faltas", number = player.stats.fouls)
                        StatsText(
                            stat = "Faltas recibidas",
                            number = player.stats.foulsReceived
                        )
                        StatsText(stat = "Amarillas", number = player.stats.yellowCards)
                        StatsText(stat = "Rojas", number = player.stats.redCards)
                    }
                }
            })
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = MaterialTheme.spacing.default,
                    horizontal = MaterialTheme.spacing.medium
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                player.displayName?.apply {
                    Text(
                        text = this,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start
                    )
                }
                player.number?.apply {
                    Text(
                        text = this.toString(),
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PlayerInfoBoxPreview() {
    BestQatar2022PhotosTheme {
        PlayerInfoBox(player = PreviewPhotos.prevPlayerMessi)
    }
}