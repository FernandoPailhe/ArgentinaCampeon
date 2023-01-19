package com.ferpa.argentinacampeon.domain.businesslogic

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(downloadPath: String, context: Context): Flow<Resource<File>> = flow {
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
    }
}