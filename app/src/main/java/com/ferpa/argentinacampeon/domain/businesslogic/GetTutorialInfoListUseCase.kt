package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.ServerInfo
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTutorialInfoListUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(): Flow<Resource<List<ServerInfo?>>> = flow {
        try {
            emit(Resource.Loading())
            val info = repository.getTutorialInfo()
            info?.let {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}