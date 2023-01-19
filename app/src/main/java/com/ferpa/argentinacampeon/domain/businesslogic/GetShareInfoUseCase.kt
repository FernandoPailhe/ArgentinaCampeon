package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import javax.inject.Inject

class GetShareInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(): String  {
        return try {
            var info = ""
            repository.getShareInfo()?.let {
                val info = it.content ?: ""
            }
            info
        } catch (e: Exception) {
            ""
        }
    }

}