package com.ferpa.argentinacampeon.presentation.main_activity


import com.ferpa.argentinacampeon.domain.model.ServerInfo

data class InfoListState(
    val isLoading: Boolean = false,
    val serverInfo: List<ServerInfo?> = emptyList(),
    val error: String = ""
)
