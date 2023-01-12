package com.ferpa.argentinacampeon.data.remote.dto


import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.model.Stats

data class PlayerDto(
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
    val stats: Stats? = null
)

fun PlayerDto.toLocalPlayer(oldPlayer: Player? = null): Player {
    return if (oldPlayer == null) {
        Player(
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
    } else {
        Player(
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
            stats = this.stats,
            localVotes = oldPlayer.localVotes,
            localVersus = oldPlayer.localVersus,
            localRank = oldPlayer.localRank
        )
    }
}

