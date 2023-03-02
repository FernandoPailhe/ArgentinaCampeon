package com.ferpa.argentinacampeon.presentation.best_photos


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.argentinacampeon.common.AnalyticsEvents
import com.ferpa.argentinacampeon.common.AnalyticsEvents.BEST_BLOCKED_PHOTO_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.BEST_MATCH_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.BEST_PHOTO_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.BEST_PLAYER_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.BEST_TAG_CLICK
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.domain.model.getPhotoUrl
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.best_photos.components.CardPhotoListItem
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.google.firebase.analytics.FirebaseAnalytics

@Composable
fun BestPhotosScreen(
    navController: NavController,
    firebaseAnalytics: FirebaseAnalytics,
    viewModel: BestPhotosViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val favoritesState = viewModel.favoriteState.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (favoritesState.favorites.size == state.photos.size) {
                items(state.photos.size) { index ->
                    CardPhotoListItem(
                        photo = state.photos[index],
                        position = index + 1,
                        isFavorite = if (favoritesState.favorites.size > index) {
                            viewModel.favoriteState.value.favorites[index]
                        } else {
                            false
                        },
                        onItemClick = { photo ->
                                firebaseAnalytics.logSingleEvent(BEST_PHOTO_CLICK)
                                navController.navigate(
                                    Screen.PhotoDetailScreenRoute.createRoute(
                                        photo.id
                                    )
                                )
                        },
                        onPlayerClick = {
                            firebaseAnalytics.logSingleEvent(BEST_PLAYER_CLICK)
                            navController.navigate(
                                Screen.PhotoListByPlayerScreenRoute.createRoute(
                                    it
                                )
                            )
                        },
                        onMatchClick = {
                            firebaseAnalytics.logSingleEvent(BEST_MATCH_CLICK)
                            navController.navigate(
                                Screen.PhotoListByMatchScreenRoute.createRoute(
                                    it
                                )
                            )
                        },
                        onTagClick = {
                            firebaseAnalytics.logSingleEvent(BEST_TAG_CLICK)
                            navController.navigate(
                                Screen.PhotoListByTagScreenRoute.createRoute(
                                    it
                                )
                            )
                        },
                    onBookMarkClick = {
                        viewModel.switchFavorite(it)
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Constants.VioletDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading || favoritesState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}