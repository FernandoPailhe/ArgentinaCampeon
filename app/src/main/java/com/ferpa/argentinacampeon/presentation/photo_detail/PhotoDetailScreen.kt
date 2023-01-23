package com.ferpa.argentinacampeon.presentation.photo_detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.AnalyticsEvents
import com.ferpa.argentinacampeon.common.AnalyticsEvents.MEDIA_LINK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.PHOTOGRAPHER_LINK
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.linkIntent
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.data.remote.dto.getPhotoUrl
import com.ferpa.argentinacampeon.data.remote.dto.toLocalPhoto
import com.ferpa.argentinacampeon.domain.model.getFirstLink
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.ferpa.argentinacampeon.presentation.photo_detail.components.Hyperlink
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.MatchText
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerRow
import com.ferpa.argentinacampeon.presentation.versus.components.TagRow
import com.google.firebase.analytics.FirebaseAnalytics


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PhotoDetailScreen(
    navController: NavController,
    firebaseAnalytics: FirebaseAnalytics,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val phState = viewModel.phState.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Constants.VioletDark)
    ) {
        val photoDetail = state.photo
        val photographerDetail = phState.photographer
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState, true),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            GlideImage(
                model = photoDetail?.getPhotoUrl(),
                contentDescription = photoDetail?.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
//                            onPhotoClick()
                    },
                contentScale = ContentScale.FillWidth
            )
            MatchText(matchTitle = photoDetail?.match, onMatchClick = {
                navController.navigate(
                    Screen.PhotoListByMatchScreenRoute.createRoute(
                        it
                    )
                )
            })

            PlayerRow(players = photoDetail?.players, onPlayerClick = {
                navController.navigate(
                    Screen.PhotoListByPlayerScreenRoute.createRoute(
                        it
                    )
                )
            })

            Text(
                text = photoDetail?.description ?: "",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
            )

            TagRow(tags = photoDetail?.tags, onTagClick = {
                navController.navigate(
                    Screen.PhotoListByTagScreenRoute.createRoute(
                        it
                    )
                )
            })

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                contentAlignment = Alignment.BottomCenter,
                content = {
                    if (photographerDetail != null) {
                        Box(content = {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = Constants.LightBlueTransparent
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(1.dp),
                                    color = Constants.VioletDark
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = MaterialTheme.spacing.medium,
                                                bottom = MaterialTheme.spacing.default
                                            ),
                                        horizontalAlignment = Alignment.Start
                                    ) {

                                        val title2 =
                                            if (!photographerDetail?.name.isNullOrEmpty()) {
                                                stringResource(id = R.string.media)
                                            } else {
                                                stringResource(id = R.string.source)
                                            }

                                        photographerDetail?.name?.let { name ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = MaterialTheme.spacing.medium
                                                    )
                                                    .clickable {
                                                        if (!photographerDetail
                                                                .getFirstLink()
                                                                .isNullOrEmpty()
                                                        ) {
                                                            firebaseAnalytics.logSingleEvent(
                                                                PHOTOGRAPHER_LINK
                                                            )
                                                            context.linkIntent(photographerDetail.getFirstLink()!!)
                                                        }
                                                    }
                                            ) {
                                                photographerDetail.getFirstLink()?.let { link ->
                                                    Hyperlink(
                                                        link,
                                                        withIcon = true,
                                                        onlyIcon = true,
                                                        onClick = {
                                                            firebaseAnalytics.logSingleEvent(
                                                                PHOTOGRAPHER_LINK
                                                            )
                                                            context.linkIntent(link)
                                                        }
                                                    )
                                                }
                                                Text(
                                                    text = stringResource(id = R.string.photographer) + " " + name,
                                                    color = Color.White,
                                                    fontSize = 16.sp,
                                                    modifier = Modifier.padding(
                                                        horizontal = MaterialTheme.spacing.medium,
                                                        vertical = MaterialTheme.spacing.default
                                                    )
                                                )
                                            }
                                        }

                                        photographerDetail?.source?.let { source ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = MaterialTheme.spacing.medium
                                                    )
                                                    .clickable {
                                                        if (!photographerDetail.web.isNullOrEmpty()) {
                                                            firebaseAnalytics.logSingleEvent(
                                                                MEDIA_LINK
                                                            )
                                                            context.linkIntent(photographerDetail.web!!)
                                                        }
                                                    }
                                            ) {
                                                photographerDetail?.web?.let { link ->
                                                    Hyperlink(link,
                                                        withIcon = true,
                                                        onlyIcon = true,
                                                        onClick = {
                                                            firebaseAnalytics.logSingleEvent(
                                                                MEDIA_LINK
                                                            )
                                                            context.linkIntent(link)
                                                        }
                                                    )
                                                }
                                                Text(
                                                    text = "$title2 $source",
                                                    color = Color.White,
                                                    fontSize = 16.sp,
                                                    modifier = Modifier.padding(
                                                        horizontal = MaterialTheme.spacing.medium,
                                                        vertical = MaterialTheme.spacing.default
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter,
                                content = {

                                    Surface(
                                        modifier = Modifier
                                            .height(Constants.ICON_SIZE.dp)
                                            .width(Constants.ICON_SIZE.dp * 1.5f)
                                            .offset(
                                                y = -Constants.ICON_SIZE.dp / 2
                                            ), color = Constants.VioletDark
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PhotoCamera,
                                            tint = Constants.LightBlue,
                                            contentDescription = "photographer",
                                            modifier = Modifier
                                                .size(Constants.ICON_SIZE.dp),
                                        )
                                    }
                                })
                        })
                    }
                    if (phState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                })
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.default), horizontalArrangement = Arrangement.Center, content = {
                Card(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.default),
                    shape = MaterialTheme.shapes.small,
                    backgroundColor = Constants.LightBlue,
                    elevation = 8.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                firebaseAnalytics.logSingleEvent(AnalyticsEvents.DET_SHARE_IMAGE)
                                photoDetail?.toLocalPhoto(false)
                                    ?.let { viewModel.shareImage(it, context) }
                            },
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                tint = Color.White,
                                contentDescription = "send",
                                modifier = Modifier
                                    .size(Constants.ICON_SIZE.dp)
                                    .padding(end = MaterialTheme.spacing.default)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.share_button),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.default),
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp
                        )
                    }
                }
            })
        }

        Log.d("PhotoDetail", state.photo?.photoUrl.toString())
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomGradient()
        }
    }
}
