package com.ferpa.argentinacampeon.presentation.select_list


import com.ferpa.argentinacampeon.domain.model.*

data class SelectListState(
    val isLoading: Boolean = false,
    val matches: List<Match>? = emptyList(),
    val players: List<PlayerItem>? = emptyList(),
    val photographers: List<Photographer>? = emptyList(),
    val tags: List<Tag>? = emptyList(),
    val error: String = ""
)
