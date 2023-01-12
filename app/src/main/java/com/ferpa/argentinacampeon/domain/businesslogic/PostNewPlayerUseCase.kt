package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import javax.inject.Inject

class PostNewPlayerUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(player: Player) : String{
        return try {
            repository.postPlayer(player)
            Log.d(TAG, "Succes ${player.displayName}")
            "Succes player.displayName"
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.toString())
            e.toString()
        }
    }

    companion object {
        const val TAG = "PostNewPlayerUseCase"
    }

}