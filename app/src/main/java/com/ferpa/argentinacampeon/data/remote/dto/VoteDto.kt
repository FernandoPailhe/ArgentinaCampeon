package com.ferpa.argentinacampeon.data.remote.dto

data class VoteDto(
    val voteWin: String? = "",
    val voteLost: String? = "",
    val superVote: Boolean = false,
)
