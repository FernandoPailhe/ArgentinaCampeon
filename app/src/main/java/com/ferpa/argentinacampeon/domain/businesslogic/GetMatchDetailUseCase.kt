package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Match
import com.ferpa.argentinacampeon.domain.model.getMatchTitle
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetMatchDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(matchId: String): Flow<Resource<Match>> = flow {
        Log.d("getMatchDetailUseCase", "running")
        try {
            emit(Resource.Loading())
            val match = repository.getMatchDetail(matchId)
            Log.d("getMatchDetailUseCase", match.getMatchTitle())
            emit(Resource.Success(match))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}