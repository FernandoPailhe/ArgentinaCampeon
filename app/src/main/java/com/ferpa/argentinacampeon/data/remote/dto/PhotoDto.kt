package com.ferpa.argentinacampeon.data.remote.dto

import androidx.room.PrimaryKey
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService
import com.ferpa.argentinacampeon.domain.model.*

data class PhotoDto(
    @PrimaryKey
    val id: String,
    val insertDate: String?,
    val lastUpdate: String? = "",
    val votesUpdate: String? = "",
    val photoUrl: String?,
    val match: MatchTitle? = null,
    val players: List<PlayerTitle?>? = null,
    val photographer: PhotographerTitle? = null,
    val tags: List<Tag?>? = null,
    val rank: Double? = 0.0,
    val description: String? = null,
    val photoType: String? = null,
    val momentId: MomentTitle? = null,
    val rarity: Int? = 0
)

fun PhotoDto.toLocalPhoto(): Photo {
    return Photo(
        id = this.id,
        insertDate = this.insertDate,
        lastUpdate = this.lastUpdate,
        votesUpdate = this.votesUpdate,
        photoUrl = this.photoUrl,
        match = this.match,
        players = this.players,
        photographer = this.photographer,
        tags = this.tags,
        rank = this.rank,
        description = this.description,
        photoType = this.photoType,
        moment = this.momentId,
        rarity = this.rarity
    )
}

fun PhotoDto.toExistingLocalPhoto(photo: Photo): Photo {
    return Photo(
        id = this.id,
        insertDate = this.insertDate,
        lastUpdate = this.lastUpdate,
        votesUpdate = this.votesUpdate,
        photoUrl = this.photoUrl,
        match = this.match,
        players = this.players,
        photographer = this.photographer,
        tags = this.tags,
        rank = this.rank,
        description = this.description,
        photoType = this.photoType,
        moment = this.momentId,
        rarity = this.rarity,
        localVotes = photo.localVotes,
        localVersus = photo.localVersus,
        localRank = photo.localRank,
        versusIds = photo.versusIds
    )
}

fun PhotoDto.getRank(): String {
    return if (this.rank != null) {
        val rank = (this.rank * 100)
        val difference = 100 - rank
        (rank + difference / 1.5).toString().substring(0, 4)
    } else ""
}

fun PhotoDto.toHiddenPhoto() = this.copy(photoUrl = "")

fun PhotoDto.getPhotoUrl(): String {
    return if (this.photoUrl.isNullOrEmpty()) {
        ""
    } else {
        "${ArgentinaCampeonService.BASE_URL}${ArgentinaCampeonService.PHOTO_PATH}/${
            this.photoUrl.split("/").last()
        }"
    }
}
