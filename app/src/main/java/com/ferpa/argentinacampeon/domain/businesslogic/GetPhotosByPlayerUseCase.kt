package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhotosByPlayerUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(playerId: String): Flow<Resource<List<PhotoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val photos = repository.getPhotosByPlayer(playerId)
            emit(Resource.Success(photos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could´t reach server. Check your internet connexion"))
        }
    }

}