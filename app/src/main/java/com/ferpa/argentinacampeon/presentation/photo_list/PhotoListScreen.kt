package com.ferpa.argentinacampeon.presentation.photo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.photo_list.components.TagInfoBox
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.*
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.ferpa.argentinacampeon.presentation.photo_list.components.MatchInfoBox
import com.ferpa.argentinacampeon.presentation.photo_list.components.PlayerInfoBox

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun PhotoListScreen(
    navController: NavController,
    viewModel: PhotoListViewModel = hiltViewModel()
) {
    val listState = viewModel.photoListState.value
    val infoState = viewModel.infoState.value
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val lazyGridState = rememberLazyGridState()
        val availablePhotos = listState.photos.filter { !it.photoUrl.isNullOrEmpty() }

        when (infoState.info) {
            is Player -> {
                PlayerInfoBox(
                    player = infoState.info,
                    bestPhoto = if (availablePhotos.isNotEmpty()) availablePhotos.first()
                        .getPhotoUrl() else null
                )
            }
            is Match -> {
                MatchInfoBox(match = infoState.info)
            }
            is Tag -> {
                TagInfoBox(
                    tag = infoState.info,
                    bestPhoto = if (availablePhotos.isNotEmpty()) availablePhotos.first()
                        .getPhotoUrl() else null,
                    photosCount = listState.photos.size
                )
            }
            is Photographer -> {

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Constants.VioletDark)
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                state = lazyGridState
            ) {
                items(listState.photos.size) { index ->
                    val photo = listState.photos[index]
                    GlideImage(
                        model = if (photo.photoUrl.isNullOrEmpty()) R.drawable.fot_bloqueada else photo.getPhotoUrl(),
                        contentDescription = photo.description,
                        modifier = Modifier
                            .height(120.dp)
                            .clickable {
                                if (!photo.photoUrl.isNullOrEmpty()) {
                                    navController.navigate(
                                        Screen.PhotoDetailScreenRoute.createRoute(photo.id)
                                    )
                                }
                            }
                            .padding(vertical = MaterialTheme.spacing.nano),
                        contentScale = ContentScale.FillHeight,
                    )
                }
            }
            if (listState.error.isNotBlank()) {
                Text(
                    text = listState.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .align(Alignment.Center)
                )
            }
            if (listState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomGradient(0.02f)
            }
        }
    }
}