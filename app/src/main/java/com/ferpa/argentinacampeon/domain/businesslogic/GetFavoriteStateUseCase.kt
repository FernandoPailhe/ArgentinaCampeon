package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteStateUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(photoId: String): Flow<Boolean> = flow {
        try {
            val favoriteState = repository.getFavoriteStateById(photoId)
            emit(favoriteState)
        } catch (e: Exception) {
            Log.e("GetFavoriteStateUseCase", e.localizedMessage ?: "An unexpected error occurred")
        }
    }

}