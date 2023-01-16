package com.ferpa.argentinacampeon.presentation.versus.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.data.previewsource.PreviewPhotos
import com.ferpa.argentinacampeon.data.remote.dto.toLocalPhoto
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.getPhotoUrl
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing

private enum class HeartState {
    Gone,
    Voted
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun VersusPhotoBox(
    modifier: Modifier = Modifier,
    photo: Photo,
    onPhotoClick: () -> Unit = {},
    photoHeight: Dp,
    tutorialPhoto: Int = 0
) {
    Box(contentAlignment = Alignment.Center) {
        var heartState by remember { mutableStateOf(HeartState.Gone) }
        val transition = updateTransition(targetState = heartState, label = "")
        val color by transition.animateColor(label = "") { state ->
            when (state) {
                HeartState.Gone -> Color.Transparent
                HeartState.Voted -> Color.White
            }
        }
        val size by transition.animateFloat(label = "") { state ->
            when (state) {
                HeartState.Gone -> 0.0f
                HeartState.Voted -> 0.5f
            }
        }
        if (tutorialPhoto == 0) {
            GlideImage(
                model = R.drawable.cargando,
                contentDescription = photo.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(photoHeight)
                    .graphicsLayer(
                        scaleX = 0.5f,
                        scaleY = 0.5f,
                    ),
                contentScale = ContentScale.FillWidth
            )
        }
        GlideImage(
            model = if (tutorialPhoto == 0) photo.getPhotoUrl() else tutorialPhoto,
            contentDescription = photo.description,
            modifier = Modifier
                .fillMaxWidth()
                .height(photoHeight)
                .combinedClickable(
                    onDoubleClick = {
                        heartState = HeartState.Voted
                        onPhotoClick()
                    }
                ) { },
            contentScale = ContentScale.FillWidth
        )
        Icon(
            modifier = Modifier.fillMaxSize(size),
            imageVector = Icons.Default.Favorite,
            tint = color,
            contentDescription = "favorite",
        )
        Box(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.spacing.small,
                    horizontal = MaterialTheme.spacing.small
                )
                .align(Alignment.BottomEnd)
        ) {
            photo.photographer?.apply {
                AuthorBox(author = this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VersusPhotoBoxPreview() {
    BestQatar2022PhotosTheme {
        PreviewPhotos.prevPhotoTitle.toLocalPhoto()
    }
}


