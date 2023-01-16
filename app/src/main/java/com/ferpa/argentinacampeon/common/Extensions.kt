package com.ferpa.argentinacampeon.common

import android.content.Context
import android.content.pm.PackageManager
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
import com.ferpa.argentinacampeon.data.remote.dto.toLocalPhoto
import com.ferpa.argentinacampeon.domain.model.Photo

object Extensions {

    fun List<PhotoDto>.toHiddenPhotoList(votedIds: List<String>): List<Photo> =
        this.map { photoDto ->
            photoDto.toLocalPhoto(hiddenPhoto = !votedIds.contains(photoDto.id))
        }

    fun List<Pair<Photo, Photo>?>.toPairIdList(): List<Pair<String, String>> =
        this.map {
            if (it?.first?.id != null) {
                Pair(
                    it.first.id,
                    it.second.id
                )
            } else {
                Pair("", "")
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

    fun Context.appVersion(): String {
        return try {
            packageManager.getPackageInfo(packageName, 0).versionName
        } catch (ex: PackageManager.NameNotFoundException) {
            ""
        }
    }

}