package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetPlayerDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(playerId: String): Flow<Resource<Player>> = flow {
        try {
            emit(Resource.Loading())
            val player = repository.getPlayerDetail(playerId)
            Log.d("getPlayerDetailUseCase",player.displayName.toString() )
            emit(Resource.Success(player))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}