package com.ferpa.argentinacampeon.domain.businesslogic

import android.util.Log
import com.ferpa.argentinacampeon.domain.model.Vote
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostVoteUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(vote: Vote, isTutorial: Boolean = false) {
        try {
            if (isTutorial){
                repository.onlyLocalVote(vote)
                Log.d("postVoteOnlyLocal", vote.photoWin.toString() + " - " + vote.photoLost.toString())
            } else {
                repository.postVote(vote)
                Log.d("postVote", vote.photoWin.toString() + " - " + vote.photoLost.toString())
            }
        } catch (e: HttpException) {
            Log.d("postVote", e.message())
        } catch (e: IOException) {
            Log.d("postVote2", e.toString())
        }
    }

}