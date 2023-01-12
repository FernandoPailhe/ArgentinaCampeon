package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import javax.inject.Inject

class SwitchFavoriteUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(photoId: String) {
        try {
            repository.switchFavorite(photoId)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Unexpected error")
        }
    }

    companion object {
        const val TAG = "SwitchFavoriteUseCase"
    }

}