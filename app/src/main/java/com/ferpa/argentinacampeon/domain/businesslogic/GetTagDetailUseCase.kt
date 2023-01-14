package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Match
import com.ferpa.argentinacampeon.domain.model.Tag
import com.ferpa.argentinacampeon.domain.model.getMatchTitle
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetTagDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(tagId: String): Flow<Resource<Tag>> = flow {
        Log.d("getTagDetailUseCase", "running")
        try {
            emit(Resource.Loading())
            val tag = repository.getTagDetail(tagId)
            Log.d("getTagDetailUseCase", tag.tag ?: "null tag")
            emit(Resource.Success(tag))
        } catch (e: Exception) {
            Log.d("getTagDetailUseCase", e.localizedMessage ?: "An unexpected error occurred")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}