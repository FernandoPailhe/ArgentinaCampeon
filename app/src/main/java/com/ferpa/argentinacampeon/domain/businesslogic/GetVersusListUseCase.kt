package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVersusListUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(ignorePair: Pair<String, String> = Pair("","")): Flow<Resource<List<Pair<Photo, Photo>>>> = flow {
        try {
            emit(Resource.Loading())
            val photos = repository.getVersusList(ignorePair)
            emit(Resource.Success(photos))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}