package com.ferpa.argentinacampeon.presentation.select_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.PlayerItem
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import okhttp3.internal.wait

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun PlayerItemCard(player: PlayerItem, onPlayerClick: (String) -> Unit = {}) {
    player.displayName?.let {
        Card(
            modifier = Modifier
                .padding(
                    MaterialTheme.spacing.extraSmall
                ),
            shape = shapes.small,
            elevation = 8.dp,
            backgroundColor = Constants.Violet,
            onClick = { onPlayerClick(player.id) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.BottomCenter,
                content = {

                    player.photoUrl?.let {
                        GlideImage(
                            model = it,
                            contentDescription = player.displayName,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = player.displayName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.default),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Constants.VioletDark,
                                offset = Offset(2.5f, 2.0f),
                                blurRadius = 2f
                            ),
                            fontFamily = FontFamily(Font(R.font.qatar)),
                            fontWeight = FontWeight.Light,
                            letterSpacing = 3.sp
                        )
                    )

                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd, content = {

                            Text(
                                text = stringResource(id = R.string.photos) + player.photoCount.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.default),
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Constants.VioletDark,
                                        offset = Offset(2.5f, 2.0f),
                                        blurRadius = 2f
                                    ),
                                    fontFamily = FontFamily(Font(R.font.qatar)),
                                    fontWeight = FontWeight.Light,
                                    letterSpacing = 3.sp,
                                    textAlign = TextAlign.Center
                                )
                            )

                        })
                })
        }
    }
}
