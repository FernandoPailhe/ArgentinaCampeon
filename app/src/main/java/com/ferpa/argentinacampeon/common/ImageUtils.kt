package com.ferpa.argentinacampeon.common

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ferpa.argentinacampeon.R



const val DEFAULT_PLACEHOLDER = R.drawable.cargando
const val TAG = "ImageUtils"

@SuppressLint("UnrememberedMutableState")
@Composable
fun loadPicture(
    imgUrl: String?,
    @DrawableRes defaultImageResource: Int
): MutableState<Bitmap?> {

    Log.d(TAG, imgUrl.toString())

    val context = LocalContext.current

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    Glide.with(context)
        .asBitmap()
        .load(defaultImageResource)
        .into(object: CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                 bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })

    if (imgUrl == null) return bitmapState

    Glide.with(context)
        .asBitmap()
        .load(imgUrl)
        .into(object: CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })

    return bitmapState
}