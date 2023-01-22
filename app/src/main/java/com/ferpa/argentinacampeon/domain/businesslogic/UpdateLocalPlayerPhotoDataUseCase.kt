package com.ferpa.argentinacampeon.domain.businesslogic

import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateLocalPlayerPhotoDataUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(): Boolean  {
        try {
            return repository.updatePlayersProfile().first()
        } catch (e: HttpException) {
            return false
        } catch (e: IOException) {
            return false
        } catch (e: Exception) {
            return false
        }
    }

}