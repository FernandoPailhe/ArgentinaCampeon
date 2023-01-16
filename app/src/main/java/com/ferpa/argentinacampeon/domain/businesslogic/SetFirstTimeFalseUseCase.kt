package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import javax.inject.Inject

class SetFirstTimeFalseUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke() {
        try {
            repository.setFirstTime(false)
        } catch (e: Exception) {
            Log.e("SetFirstTimeUseCase", e.localizedMessage ?: "An unexpected error occurred")
        }
    }

}