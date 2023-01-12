package com.ferpa.argentinacampeon.domain.model

data class Goal(
    val id: String?,
    val matchId: String? = "",
    val team: String? = "",
    val playerId: String? = "",
    val photosId: List<String?>? = emptyList(),
    val lastUpdate: String? = "",
    val momentId: String? = "",
    val teamAgainst: String? = ""
)
