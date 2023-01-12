package com.ferpa.argentinacampeon.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.data.remote.dto.toHiddenPhoto

object Extensions {

    fun List<PhotoDto>.toHiddenPhotoList(votedIds: List<String>): List<PhotoDto> {
        return this.map { photoDto ->
            if (votedIds.contains(photoDto.id)) {
                photoDto
            } else {
                photoDto.toHiddenPhoto()
            }
        }
    }

    fun Modifier.rotating(duration: Int): Modifier = composed {
        val transition = rememberInfiniteTransition()
        val angleRatio by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(duration)
            )
        )
        graphicsLayer(rotationZ = 360f * angleRatio)
    }

}