package com.ferpa.argentinacampeon.presentation.favorites

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.R.string.empty_favorites
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.best_photos.components.CardPhotoListItem
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.google.firebase.analytics.FirebaseAnalytics


@Composable
fun FavoritePhotosScreen(
    navController: NavController,
    firebaseAnalytics: FirebaseAnalytics,
    viewModel: FavoritePhotosViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.photos.size) { index ->
                CardPhotoListItem(
                    photo = state.photos[index],
                    position = index + 1,
                    isFavorite = true,
                    onItemClick = { photo ->
                        navController.navigate(Screen.PhotoDetailScreenRoute.createRoute(photo.id))
                    },
                    onPlayerClick = {
                        navController.navigate(
                            Screen.PhotoListByPlayerScreenRoute.createRoute(
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
                    onTagClick = {
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
        if (state.photos.isEmpty() && !state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center, content = {
                Text(
                    text = stringResource(id = empty_favorites),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Constants.VioletDark,
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.medium
                        )
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                )
            })
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red,
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
    }
}