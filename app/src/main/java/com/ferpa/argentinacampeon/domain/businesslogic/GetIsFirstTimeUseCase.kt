package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetIsFirstTimeUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(): Flow<Boolean> = flow {
        try {
            emit(repository.checkFirstTime())
        } catch (e: Exception) {
            Log.e("GetIsFirstTimeUseCase", e.localizedMessage ?: "An unexpected error occurred")
        }
    }

}