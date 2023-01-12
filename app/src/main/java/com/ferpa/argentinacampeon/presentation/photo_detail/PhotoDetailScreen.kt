package com.ferpa.argentinacampeon.presentation.photo_detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.data.remote.dto.getPhotoUrl
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradientViolet
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.MatchText
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerRow
import com.ferpa.argentinacampeon.presentation.versus.components.TagRow


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PhotoDetailScreen(
    navController: NavController,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Constants.VioletDark)
    ) {
        Box(modifier = Modifier.clickable {
//            onPhotoClick()
        }) {
            val photoDetail = state.photo


            Column(modifier = Modifier.fillMaxSize()) {
                GlideImage(
                    model = photoDetail?.getPhotoUrl(),
                    contentDescription = photoDetail?.description,
                    modifier = Modifier.fillMaxWidth(),
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

            }
        }
        Log.d("PhotoDetail", state.photo?.photoUrl.toString())
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
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomGradientViolet()
        }
    }
}
