package com.ferpa.argentinacampeon.presentation.main_activity


import com.ferpa.argentinacampeon.domain.model.InfoFromApi

data class InfoListState(
    val isLoading: Boolean = false,
    val infoFromApi: List<InfoFromApi?> = emptyList(),
    val error: String = ""
)
