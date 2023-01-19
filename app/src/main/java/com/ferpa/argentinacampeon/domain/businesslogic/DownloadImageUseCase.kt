package com.ferpa.argentinacampeon.domain.businesslogic

import android.content.Context
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(downloadPath: String, context: Context): Flow<Resource<File>> = flow {
        try {
            val response = repository.downloadImage(downloadPath)
            emit(Resource.Loading())
            response.body()?.let { body ->
                val file = File(context.cacheDir, "image.jpg")
                val outputStream = java.io.FileOutputStream(file)
                outputStream.use { stream ->
                    try {
                        stream.write(body.bytes())
                        emit(Resource.Success(file))
                    } catch (e: IOException) {
                        emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
                    }
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could´t reach server. Check your internet connexion"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}