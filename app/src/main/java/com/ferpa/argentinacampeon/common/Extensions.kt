package com.ferpa.argentinacampeon.common

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.core.content.FileProvider
import com.ferpa.argentinacampeon.common.AnalyticsEvents.ADD_TO_FAVORITE
import com.ferpa.argentinacampeon.common.AnalyticsEvents.DELETE_FAVORITE
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.data.remote.dto.toHiddenPhoto
import com.ferpa.argentinacampeon.data.remote.dto.toLocalPhoto
import com.ferpa.argentinacampeon.domain.model.Photo
import com.google.firebase.analytics.FirebaseAnalytics
import java.io.File

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

    fun File.toSharingUri(context: Context): Uri? {

        var uri: Uri? = null
        try {
            uri = FileProvider.getUriForFile(
                context,
                "com.ferpa.argentinacampeon.fileprovider", this
            )
        } catch (e: java.lang.Exception) {
            Log.e("toSharingUri", "getUriFromFileToShare " + e.message)
        }
        Log.d("toSharingUri", uri?.path.toString())
        return uri

    }


   /**
    *  Extension function to log event in Firebase Analytics.
    *  Call this function from a Fragment Class with String of event to log.
    */
    fun FirebaseAnalytics.logSingleEvent(event: String){
        val bundle = Bundle()
        bundle.putString("eventLog", event)
        this.logEvent(event, bundle)
    }

    fun FirebaseAnalytics.logSwitchFavorite(oldStatus: Boolean){
        if (oldStatus) {
            this.logSingleEvent(DELETE_FAVORITE)
        } else {
            this.logSingleEvent(ADD_TO_FAVORITE)
        }
    }

}