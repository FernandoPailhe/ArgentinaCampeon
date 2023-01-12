package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ferpa.argentinacampeon.data.remote.dto.PlayerDto

@Entity
data class Player(
    @PrimaryKey
    val id: String = "",
    val displayName: String? = "",
    val name: String = "",
    val nickName: String? = "",
    val birthday: String? = null,
    val position: String? = null,
    val nationalTeam: String? = "",
    val number: Int? = null,
    val team: String? = "",
    val profilePhotoUrl: String? = "",
    val lastUpdate: String? = "",
    val stats: Stats? = null,
    val localVotes: Int = 0,
    val localVersus: Int = 0,
    val localRank: Double? = 0.0,
)

fun Player.updateVoteWin(): Player  {
    val localVotes = this.localVotes.inc()
    val localVersus = this.localVersus.inc()
    val localRank = localVotes.divideToPercent(localVersus)
    return this.copy(localVotes = localVotes, localVersus =  localVersus, localRank = localRank)
}

fun Player.updateVoteLost(): Player {
    val localVersus = this.localVersus.inc()
    val localRank = localVotes.divideToPercent(this.localVotes)
    return this.copy(localVersus = localVersus, localRank = localRank)
}

fun Player.toPlayerDto(): PlayerDto {
    return PlayerDto(
        id = this.id,
        displayName = this.displayName,
        name = this.name,
        nickName = this.nickName,
        birthday = this.birthday,
        position = this.position,
        nationalTeam = this.nationalTeam,
        number = this.number,
        team = this.team,
        profilePhotoUrl = this.profilePhotoUrl,
        lastUpdate = this.lastUpdate,
        stats = this.stats
    )
}

