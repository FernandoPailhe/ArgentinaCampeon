package com.ferpa.argentinacampeon.presentation.admin.edit


import com.ferpa.argentinacampeon.domain.model.*

data class AllPlayersListState(
    val isLoading: Boolean = false,
    val players: List<PlayerTitle>? = emptyList(),
    val error: String = ""
)
