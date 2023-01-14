package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Photographer
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetPhotographerDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(photographerId: String): Flow<Resource<Photographer>> = flow {
        try {
            emit(Resource.Loading())
            val photographer = repository.getPhotographerDetail(photographerId)
            Log.d("getPhotographerDetailUseCase", photographer.name ?: "null photographer name")
            emit(Resource.Success(photographer))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}