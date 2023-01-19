package com.ferpa.argentinacampeon.presentation.versus.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.ferpa.argentinacampeon.common.AnalyticsEvents.SHARE_IMAGE
import com.ferpa.argentinacampeon.common.AnalyticsEvents.VOTE_BOTTOM
import com.ferpa.argentinacampeon.common.AnalyticsEvents.VOTE_TOP
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.common.Extensions.logSwitchFavorite
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.Vote
import com.ferpa.argentinacampeon.domain.model.getDownloadUrl
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.google.android.gms.dynamic.IFragmentWrapper
import com.google.firebase.analytics.FirebaseAnalytics


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
    onBookmarkClick: (String) -> Unit = {},
    onSendClick: (String) -> Unit = {},
    firebaseAnalytics: FirebaseAnalytics
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val photoHeight = screenHeight / 2.5f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = MaterialTheme.spacing.extraSmall),
        verticalArrangement = Arrangement.SpaceAround
    ) {

//        TagRow(tags = photoPair.first.tags, onTagClick = onTagClick)

        PlayerRow(players = photoPair.first.players, onPlayerClick = onPlayerClick)

        MatchText(matchTitle = photoPair.first.match, onMatchClick = onMatchClick)

        Box(
            modifier = Modifier.fillMaxHeight(0.42f)
        ) {
            VersusPhotoBox(
                photo = photoPair.first,
                onPhotoClick = {
                    firebaseAnalytics.logSingleEvent(VOTE_TOP)
                    onPhotoClick(Vote(photoPair.first, photoPair.second))
                },
                photoHeight = photoHeight
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd,
//                contentAlignment = Alignment.CenterEnd,
                content = {
                    Icons(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding( horizontal = MaterialTheme.spacing.default,
                            vertical = MaterialTheme.spacing.medium)
//                            .padding(MaterialTheme.spacing.default)
                            .fillMaxHeight(0.8f),
                        isFavorite = bookmarkPair.first,
                        onBookmarkClick = {
                            firebaseAnalytics.logSwitchFavorite(bookmarkPair.first)
                            onBookmarkClick(photoPair.first.id)
                        },
                        onSendClick = {
                            photoPair.first.getDownloadUrl()?.let { downloadUrl ->
                                firebaseAnalytics.logSingleEvent(SHARE_IMAGE)
                                onSendClick(downloadUrl)
                            }
                        }
                    )
                })
        }

        Box(
            modifier = Modifier.fillMaxHeight(0.66f)
        ) {
            VersusPhotoBox(
                photo = photoPair.second,
                onPhotoClick = {
                    firebaseAnalytics.logSingleEvent(VOTE_BOTTOM)
                    onPhotoClick(Vote(photoPair.second, photoPair.first))
                },
                photoHeight = photoHeight
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
                content = {

                    Icons(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding( horizontal = MaterialTheme.spacing.default,
                                vertical = MaterialTheme.spacing.medium)
//                            .padding(MaterialTheme.spacing.default)
                            .fillMaxHeight(0.8f),
                        isFavorite = bookmarkPair.second,
                        onBookmarkClick = {
                            firebaseAnalytics.logSwitchFavorite(bookmarkPair.second)
                            onBookmarkClick(photoPair.second.id)
                        },
                        onSendClick = {
                            photoPair.second.getDownloadUrl()?.let { downloadUrl ->
                                firebaseAnalytics.logSingleEvent(SHARE_IMAGE)
                                onSendClick(downloadUrl)
                            }
                        }
                    )

                })
        }
        MatchText(matchTitle = photoPair.second.match, onMatchClick = onMatchClick)

        PlayerRow(players = photoPair.second.players, onPlayerClick = onPlayerClick)

//        TagRow(tags = photoPair.second.tags, onTagClick = onTagClick)

    }

}

/**
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
 */

