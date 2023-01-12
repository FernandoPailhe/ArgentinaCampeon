package com.ferpa.argentinacampeon.presentation.main_activity


import com.ferpa.argentinacampeon.domain.model.Photo

data class VersusListState(
    val isLoading: Boolean = false,
    val photos: List<Pair<Photo, Photo>?> = emptyList(),
    val error: String = ""
)
