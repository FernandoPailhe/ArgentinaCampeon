package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import javax.inject.Inject

class GetVersusPairUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    /*
    operator fun invoke(): Flow<Resource<Pair<LocalPhoto, LocalPhoto>>> = flow {
        try {
            emit(Resource.Loading())
            val versusPair = repository.getVersusPair()
            Log.d("GetVersusPairUseCase", versusPair.first().first.photoUrl.toString())
            Log.d("GetVersusPairUseCase", versusPair.first().second.photoUrl.toString())
            Resource.Success(versusPair)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

     */

}