@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.ferpa.argentinacampeon.presentation.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.getRank
import com.ferpa.argentinacampeon.presentation.common.components.RateIconColumn
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.MatchText
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerRow
import com.ferpa.argentinacampeon.presentation.versus.components.TagRow
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.domain.model.getPhotoUrl

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun LocalPhotoListItem(
    modifier: Modifier = Modifier,
    photo: Photo,
    onItemClick: (Photo) -> Unit = {},
    onPlayerClick: (String) -> Unit = {},
    onMatchClick: (String) -> Unit = {},
    onTagClick: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                vertical = MaterialTheme.spacing.small,
                horizontal = MaterialTheme.spacing.default
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
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
                    model = if (photo.photoUrl.isNullOrEmpty()) R.drawable.fot_bloqueada else photo.getPhotoUrl(),
                    contentDescription = photo.description,
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Column {
                    MatchText(matchTitle = photo.match, onMatchClick, true)
                    if (photo.rank != null) RateIconColumn(icon = R.drawable.ic_baseline_star_rate_24, text = "${photo.getRank()}")
                    PlayerRow(
                        modifier = Modifier.fillMaxHeight(0.25f),
                        players = photo.players,
                        onPlayerClick = onPlayerClick
                    )
                    TagRow(tags = photo.tags, isCard = false, onTagClick = onTagClick)
                }
            }
        }
    }
}