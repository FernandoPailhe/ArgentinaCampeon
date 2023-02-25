package com.ferpa.argentinacampeon.presentation.versus

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.AnalyticsEvents
import com.ferpa.argentinacampeon.common.AnalyticsEvents.VERSUS_MATCH_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.VERSUS_PLAYER_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.VERSUS_SHARE_IMAGE
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.POST_VOTE_DELAY
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.about_us.AppInfoBlock
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.PairPhotoItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.VerticalPager
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun VersusScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    pagerState: PagerState,
    versusViewModel: VersusViewModel = hiltViewModel(),
    onDataLoaded: () -> Unit,
    firebaseAnalytics: FirebaseAnalytics
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pairList = mainViewModel.versusListState.value.photos
    val pairFavoritesList = mainViewModel.favoriteState.value.favorites
    if (pairList.isEmpty()) return
    onDataLoaded()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Constants.VioletDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Constants.VioletDark),
            verticalArrangement = Arrangement.Center
        ) {
            VerticalPager(
                count = pairList.size,
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (pairList.isEmpty()) {
                    return@VerticalPager
                } else {
                    pairList[page]?.let { versusPair ->
                        if (!mainViewModel.favoriteState.value.isLoading) {
                            PairPhotoItem(
                                modifier = Modifier.fillMaxSize(),
                                photoPair = versusPair,
                                bookmarkPair = if (pairFavoritesList.size > page) {
                                    pairFavoritesList[page]
                                } else Pair(false, false),
                                onPhotoClick = {
                                    try {
                                        if (page + 2 < pairList.size) {
                                            scope.launch {
                                                delay(POST_VOTE_DELAY)
                                                pagerState.animateScrollToPage(page + 1)
                                            }
                                        } else {
                                            scope.launch {
                                                delay(POST_VOTE_DELAY)
                                                pagerState.animateScrollToPage(page + 1)
                                                mainViewModel.addNewVersusPhotos()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.e(
                                            "VersusScreen",
                                            e.localizedMessage ?: "Unknown error"
                                        )
                                    }
                                    versusViewModel.postVote(it)
                                },
                                onPlayerClick = {
                                    firebaseAnalytics.logSingleEvent(VERSUS_PLAYER_CLICK)
                                    navController.navigate(
                                        Screen.PhotoListByPlayerScreenRoute.createRoute(
                                            it
                                        )
                                    )
                                },
                                onTagClick = {
                                    navController.navigate(
                                        Screen.PhotoListByTagScreenRoute.createRoute(
                                            it
                                        )
                                    )
                                },
                                onMatchClick = {
                                    firebaseAnalytics.logSingleEvent(VERSUS_MATCH_CLICK)
                                    navController.navigate(
                                        Screen.PhotoListByMatchScreenRoute.createRoute(
                                            it
                                        )
                                    )
                                },
                                onBookmarkClick = {
                                    versusViewModel.switchFavorite(it)
                                    scope.launch {
                                        delay(POST_VOTE_DELAY)
                                        mainViewModel.getFavoritePairListStateUpdate()
                                    }
                                },
                                onSendClick = { photo ->
                                    firebaseAnalytics.logSingleEvent(VERSUS_SHARE_IMAGE)
                                    versusViewModel.shareImage(photo, context)
                                },
                                firebaseAnalytics = firebaseAnalytics
                            )
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomGradient()
        }
        if (versusViewModel.shareImageState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        if (versusViewModel.shareImageState.value.error.isNotEmpty()) {
            Dialog(onDismissRequest = { versusViewModel.closeDialog() }) {
                ErrorDialog(versusViewModel)
            }
        }
        if (!mainViewModel.isLastVersion.value) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Constants.VioletDark)
            ) {
                Dialog(onDismissRequest = { mainViewModel.isLastVersion.value }) {
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(MaterialTheme.spacing.medium)
                    ) {
                        Box(modifier = Modifier
                            .background(Constants.VioletDark),
                            contentAlignment = Alignment.Center,
                            content = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AppInfoBlock(
                                        info = mainViewModel.updateVersionInfo.value,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                                        isAMessage = AnalyticsEvents.UPDATE_MESSAGE
                                    )
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
                                                    firebaseAnalytics.let {
                                                        it.logSingleEvent(
                                                            AnalyticsEvents.FORCE_UPDATE_CLICK
                                                        )
                                                    }
                                                    mainViewModel.dismissUpdateDialog()
                                                },
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.later),
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                modifier = Modifier
                                                    .padding(MaterialTheme.spacing.default),
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 1.sp
                                            )
                                        }
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ErrorDialog(versusViewModel: VersusViewModel) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(0.8f),
        shape = RoundedCornerShape(MaterialTheme.spacing.medium)
    ) {
        val scope = rememberCoroutineScope()
        Surface(modifier = Modifier.background(Constants.VioletDark)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable { versusViewModel.closeDialog() },
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = "Ha ocurrido un error, intentelo más tarde.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(MaterialTheme.spacing.default)
                    )
                    scope.launch {
                        delay(3000)
                        versusViewModel.closeDialog()
                    }
                })
        }
    }
}
