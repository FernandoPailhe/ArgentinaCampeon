package com.ferpa.argentinacampeon.data.remote.dto

import androidx.room.PrimaryKey
import com.ferpa.argentinacampeon.common.routes.ServerRoutes.BASE_URL
import com.ferpa.argentinacampeon.common.routes.ServerRoutes.PHOTO_PATH
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

fun PhotoDto.toLocalPhoto(hiddenPhoto: Boolean = false): Photo {
    return Photo(
        id = this.id,
        insertDate = this.insertDate,
        lastUpdate = this.lastUpdate,
        votesUpdate = this.votesUpdate,
        photoUrl = if(hiddenPhoto) null else this.photoUrl,
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
        val formattedRank = (rank + difference / 1.5).toString().substring(0, 4)
        if (formattedRank.contains("100")) {
            formattedRank.substring(0, 3)
        } else if (formattedRank[3].equals("0")) {
            formattedRank.substring(0, 2)
        } else {
            formattedRank
        }
    } else ""
}

fun PhotoDto.toHiddenPhoto() = this.copy(photoUrl = "")

fun PhotoDto.getPhotoUrl(): String {
    return if (this.photoUrl.isNullOrEmpty()) {
        ""
    } else {
        if (photoUrl.contains("192.168.100.4")) {
            "${BASE_URL}${PHOTO_PATH}/${
                this.photoUrl.split(
                    "/"
                ).last()
            }"
        } else {
            "${BASE_URL}${PHOTO_PATH}/${this.photoUrl}"
        }
    }
}
