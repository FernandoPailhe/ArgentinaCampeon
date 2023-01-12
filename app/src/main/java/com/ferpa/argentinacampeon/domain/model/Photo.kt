package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService.Companion.BASE_URL
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService.Companion.PHOTO_PATH
import kotlin.math.roundToInt

@Entity
data class Photo(
    @PrimaryKey
    val id: String,
    val insertDate: String?,
    val lastUpdate: String?,
    val votesUpdate: String?,
    val photoUrl: String?,
    val match: MatchTitle? = null,
    val players: List<PlayerTitle?>? = null,
    val photographer: PhotographerTitle? = null,
    val tags: List<Tag?>? = null,
    val rank: Double? = 0.0,
    val description: String? = null,
    val photoType: String? = null,
    val moment: MomentTitle? = null,
    val rarity: Int? = 0,
    val localVotes: Int = 0,
    val localVersus: Int = 0,
    val localRank: Double? = 0.0,
    val versusIds: List<String?> = emptyList()
)

fun Photo.updateVoteWin(versusId: String): Photo  {
    val updatedList = this.versusIds.toMutableList()
    updatedList.add(versusId)
    val localVotes = this.localVotes.inc()
    val localVersus = this.localVersus.inc()
    val localRank = localVotes.divideToPercent(localVersus)
    return this.copy(localVotes = localVotes, localVersus =  localVersus, localRank = localRank, versusIds = updatedList)
}

fun Photo.updateVoteLost(versusId: String): Photo {
    val updatedList = this.versusIds.toMutableList()
    updatedList.add(versusId)
    val localVersus = this.localVersus.inc()
    val localRank = localVotes.divideToPercent(this.localVotes)
    return this.copy(localVersus = localVersus, localRank = localRank, versusIds = updatedList)
}

fun Int.divideToPercent(divideTo: Int) = if (this > 0) {
    ((this.toDouble() / divideTo) * 100).roundToInt() / 100.0
} else 0.0

fun Photo.getRank(): String {
    return if (this.rank != null) {
        val rank = (this.rank * 100)
        val difference = 100 - rank
        (rank + difference / 1.5).toString().substring(0, 4)
    } else ""
}

fun Photo.updateRank(rankUpdate: Double): Photo = this.copy(rank = rankUpdate)

fun Photo.getPhotoUrl(isPhotoList: Boolean = false): String? {
    return if ((this.localVotes == 0 && isPhotoList) || this.photoUrl == null){
        null
    } else {
        "$BASE_URL$PHOTO_PATH/${this.photoUrl.split("/").last()}"
    }
}