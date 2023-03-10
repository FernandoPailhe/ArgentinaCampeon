package com.ferpa.argentinacampeon.domain.model

data class PlayerItem(
    val id: String,
    val displayName: String? = "",
    val photoUrl: String? = "",
    val photoCount: Int? = 0
)
