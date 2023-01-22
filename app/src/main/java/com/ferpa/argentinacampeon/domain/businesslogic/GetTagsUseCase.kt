package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.model.Tag
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(search: String = ""): Flow<Resource<List<Tag>>> = flow {
        try {
            emit(Resource.Loading())
            val tag = repository.getTags(search)
            emit(Resource.Success(tag))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could´t reach server. Check your internet connexion"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }

}