package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteListStateUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(idList: List<String>): Flow<Resource<List<Boolean>>> = flow {
        try {
            emit(Resource.Loading())
            val favoriteState = repository.getFavoriteListState(idList)
            emit(Resource.Success(favoriteState))
        } catch (e: Exception) {
            Log.e("GetFavoriteStateUseCase", e.localizedMessage ?: "An unexpected error occurred")
        }
    }

}