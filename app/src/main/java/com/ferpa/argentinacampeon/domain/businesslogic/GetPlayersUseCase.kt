package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.model.PlayerItem
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(search: String = ""): Flow<Resource<List<PlayerItem>>> = flow {
        try {
            emit(Resource.Loading())
            val players = repository.getPlayers(search)
            emit(Resource.Success(players))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}