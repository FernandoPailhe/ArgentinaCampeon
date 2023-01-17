package com.ferpa.argentinacampeon.presentation.tutorial.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.AnalyticsEvents.FINISH_TUTORIAL
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.Vote
import com.ferpa.argentinacampeon.domain.model.getLocalDrawableResource
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.MatchText
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerRow
import com.ferpa.argentinacampeon.presentation.versus.components.VersusPhotoBox
import com.google.firebase.analytics.FirebaseAnalytics


@Composable
fun PairPhotoItemTutorial(
    modifier: Modifier = Modifier
        .padding(vertical = MaterialTheme.spacing.extraSmall),
    photoPair: Pair<Photo, Photo>,
    onPhotoClick: (Vote) -> Unit = {},
    onButtonClicked: () -> Unit = {},
    infoContent: String = "",
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (photoPair.first.id != "0") {
            Box(
                modifier = Modifier.fillMaxHeight(0.42f)
            ) {
                VersusPhotoBox(
                    photo = photoPair.first,
                    onPhotoClick = { onPhotoClick(Vote(photoPair.first, photoPair.second)) },
                    photoHeight = photoHeight,
                    tutorialPhoto = photoPair.first.getLocalDrawableResource()
                )
            }

            Box(
                modifier = Modifier.fillMaxHeight(0.66f)
            ) {
                VersusPhotoBox(
                    photo = photoPair.second,
                    onPhotoClick = { onPhotoClick(Vote(photoPair.second, photoPair.first)) },
                    photoHeight = photoHeight,
                    tutorialPhoto = photoPair.second.getLocalDrawableResource()
                )
            }
        }
        Text(
            text = infoContent ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.medium
                )
                .fillMaxWidth(),
        )

        if (photoPair.first.id == "0") {
            Card(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .clickable {
                        firebaseAnalytics.logSingleEvent(FINISH_TUTORIAL)
                        onButtonClicked()
                    },
                shape = MaterialTheme.shapes.small,
                backgroundColor = Constants.LightBlue,
                elevation = 8.dp
            ) {
                Text(
                    text = "Continuar",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium)
                )
            }
        }

    }

}

