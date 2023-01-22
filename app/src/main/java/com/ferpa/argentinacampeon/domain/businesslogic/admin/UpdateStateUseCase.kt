package com.ferpa.argentinacampeon.domain.businesslogic.admin

import android.util.Log
import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.domain.model.Vote
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateStateUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(id: String, rarity: Int) {
        try {
            repository.updateState(id, rarity)
        } catch (e: HttpException) {
            Log.d("postVote", e.message())
        } catch (e: IOException) {
            Log.d("postVote", e.toString())
        } catch (e: Exception) {
            Log.d("postVote", "An unexpected error occurred")
        }
    }

}