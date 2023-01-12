package com.ferpa.argentinacampeon.presentation.versus

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.POST_VOTE_DELAY
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradientViolet
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.versus.components.PairPhotoItem
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VersusScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    pagerState: PagerState,
    versusViewModel: VersusViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Constants.VioletDark,
        darkIcons = false
    )
    val scope = rememberCoroutineScope()
    val pairList = mainViewModel.versusListState.value.photos
    if (pairList.isEmpty()) return

    Box(modifier = Modifier.fillMaxSize().background(Constants.VioletDark)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Constants.VioletDark),
            verticalArrangement = Arrangement.Bottom
        ) {
            VerticalPager(
                count = pairList.size,
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                if (pairList.isEmpty()) {
                    return@VerticalPager
                } else {
                    pairList[page]?.let { versusPair ->
                        PairPhotoItem(
                            modifier = Modifier.fillMaxSize(),
                            photoPair = versusPair,
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
                                    Log.e("VersusScreen", e.localizedMessage ?: "Unknown error" )
                                }
                                versusViewModel.postVote(it)
                            },
                            onPlayerClick = {
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
                                navController.navigate(
                                    Screen.PhotoListByMatchScreenRoute.createRoute(
                                        it
                                    )
                                )
                            },
                            onBookmarkClick = {
                                versusViewModel.switchFavorite(it)
                            }
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomGradientViolet()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun VersusScreenPreview() {
    BestQatar2022PhotosTheme {
//        VersusScreen(navController = rememberNavController())
    }
}