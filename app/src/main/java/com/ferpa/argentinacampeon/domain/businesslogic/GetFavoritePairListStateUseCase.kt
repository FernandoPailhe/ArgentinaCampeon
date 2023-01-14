package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritePairListStateUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(pairIdList: List<Pair<String, String>>): Flow<Resource<List<Pair<Boolean, Boolean>>>> = flow {
        try {
            emit(Resource.Loading())
            val favoriteState = repository.getFavoritePairListState(pairIdList)
            emit(Resource.Success(favoriteState))
        } catch (e: Exception) {
            Log.e("GetFavoriteStateUseCase", e.localizedMessage ?: "An unexpected error occurred")
        }
    }

}