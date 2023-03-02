@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.ferpa.argentinacampeon.presentation.best_photos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.MatchText
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerRow
import com.ferpa.argentinacampeon.presentation.versus.components.TagRow
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Constants.CARD_PHOTO_LIST_ITEM_HEIGHT
import com.ferpa.argentinacampeon.common.Constants.ICON_SIZE
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.getPhotoUrl
import com.ferpa.argentinacampeon.domain.model.getRank
import com.ferpa.argentinacampeon.presentation.versus.components.Icons

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun CardPhotoListItem(
    photo: Photo,
    position: Int,
    isFavorite: Boolean = false,
    onItemClick: (Photo) -> Unit = {},
    onPlayerClick: (String) -> Unit = {},
    onMatchClick: (String) -> Unit = {},
    onTagClick: (String) -> Unit = {},
    onBookMarkClick: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.cardOffset,
                    top = MaterialTheme.spacing.cardOffset,
                    bottom = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.cardOffset
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CARD_PHOTO_LIST_ITEM_HEIGHT.dp)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                    .background(Constants.LightBlue),
                onClick = { onItemClick(photo) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(photo) }
                        .background(Constants.LightBlue),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GlideImage(
                        model = if (photo.getPhotoUrl().isNullOrEmpty()) R.drawable.fot_bloqueada else photo.getPhotoUrl(),
                        contentDescription = photo.description,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        MatchText(matchTitle = photo.match, onMatchClick, true)
                        PlayerRow(
                            modifier = Modifier.fillMaxHeight(0.25f),
                            players = photo.players,
                            onPlayerClick = onPlayerClick
                        )
                        Box(modifier = Modifier.padding(horizontal = ICON_SIZE.dp)) {
                            TagRow(tags = photo.tags, isCard = false, onTagClick = onTagClick)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd,
                    content = {
                        if (!photo.photoUrl.isNullOrEmpty()) {
                            Icons(
                                isFavorite = isFavorite,
                                isPhotoCard = true,
                                onBookmarkClick = { onBookMarkClick(photo.id) }
                            )
                        }
                    })
            }
        }
        Box(modifier = Modifier.padding(MaterialTheme.spacing.small)) {

            Card(
                modifier = Modifier
                    .height(MaterialTheme.spacing.positionOffset)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                    .align(Alignment.TopStart),
                elevation = MaterialTheme.spacing.medium
            ) {
                Box(
                    modifier = Modifier
                        .background(Constants.LightBlue)
                        .padding(horizontal = MaterialTheme.spacing.default),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "Rank: ${photo.getRank()}%",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(start = MaterialTheme.spacing.positionOffset)
                        )
                    })
            }

            Card(
                modifier = Modifier
                    .height(MaterialTheme.spacing.positionOffset)
                    .width(MaterialTheme.spacing.positionOffset * 1.2f)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                    .background(Constants.VioletDark)
                    .align(Alignment.TopStart),
                elevation = MaterialTheme.spacing.medium
            ) {
                Box(
                    modifier = Modifier
                        .background(Constants.Violet)
                        .padding(horizontal = MaterialTheme.spacing.default),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "$position",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .align(Alignment.Center),
                            maxLines = 1
                        )
                    })
            }
        }

    }


}