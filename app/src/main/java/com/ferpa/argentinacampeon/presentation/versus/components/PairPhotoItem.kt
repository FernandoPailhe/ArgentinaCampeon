package com.ferpa.argentinacampeon.presentation.versus.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ferpa.argentinacampeon.data.previewsource.PreviewPhotos
import com.ferpa.argentinacampeon.data.remote.dto.VoteDto
import com.ferpa.argentinacampeon.data.remote.dto.toLocalPhoto
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.PhotographerTitle
import com.ferpa.argentinacampeon.domain.model.Vote
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing


@OptIn(
    ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun PairPhotoItem(
    modifier: Modifier = Modifier
        .padding(vertical = MaterialTheme.spacing.extraSmall),
    photoPair: Pair<Photo, Photo>,
    bookmarkPair: Pair<Boolean, Boolean> = Pair(false, false),
    onPhotoClick: (Vote) -> Unit = {},
    onPlayerClick: (String) -> Unit = {},
    onTagClick: (String) -> Unit = {},
    onMatchClick: (String) -> Unit = {},
    onBookmarkClick: (String) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val photoHeight = screenHeight / 3f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {

//        TagRow(tags = photoPair.first.tags, onTagClick = onTagClick)

        PlayerRow(players = photoPair.first.players, onPlayerClick = onPlayerClick)

        MatchText(matchTitle = photoPair.first.match, onMatchClick = onMatchClick)

        Box(
            modifier = Modifier.fillMaxHeight(0.4f)
        ) {
            VersusPhotoBox(
                photo = photoPair.first,
                onPhotoClick = { onPhotoClick(Vote(photoPair.first, photoPair.second)) },
                photoHeight = photoHeight
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
                content = {

                    IconsColumn(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(MaterialTheme.spacing.default)
                            .fillMaxHeight(0.8f),
                        isFavorite = bookmarkPair.first,
                    ) { onBookmarkClick(photoPair.first.id) }

                })
        }

        Box(
            modifier = Modifier.fillMaxHeight(0.6f)
        ) {
            VersusPhotoBox(
                photo = photoPair.second,
                onPhotoClick = { onPhotoClick(Vote(photoPair.second, photoPair.first)) },
                photoHeight = photoHeight
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
                content = {

                    IconsColumn(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(MaterialTheme.spacing.default)
                            .fillMaxHeight(0.8f),
                        isFavorite = bookmarkPair.second
                    ) { onBookmarkClick(photoPair.second.id) }

                })
        }
        MatchText(matchTitle = photoPair.second.match, onMatchClick = onMatchClick)

        PlayerRow(players = photoPair.second.players, onPlayerClick = onPlayerClick)

//        TagRow(tags = photoPair.second.tags, onTagClick = onTagClick)

    }

}


@Preview(showBackground = true)
@Composable
fun PairPhotoItemBackUpPreview() {
    BestQatar2022PhotosTheme {
        PairPhotoItem(
            photoPair = Pair(
                PreviewPhotos.prevPhotoTitle.toLocalPhoto(),
                PreviewPhotos.prevPhotoTitle.toLocalPhoto()
            ),
            onPhotoClick = { VoteDto("1", "2") })
    }
}
