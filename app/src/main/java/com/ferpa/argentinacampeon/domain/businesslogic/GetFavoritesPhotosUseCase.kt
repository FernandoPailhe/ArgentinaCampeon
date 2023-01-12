package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(): Flow<Resource<List<Photo>>> = flow {
        try {
            emit(Resource.Loading())
            val photos = repository.getFavoritesPhotos()
            emit(Resource.Success(photos))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}