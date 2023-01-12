package com.ferpa.argentinacampeon.domain.model

import com.ferpa.argentinacampeon.data.remote.dto.VoteDto

data class Vote(
    val photoWin: Photo,
    val photoLost: Photo
)

fun Vote.toVoteDto(): VoteDto = VoteDto(this.photoWin.id, this.photoLost.id)