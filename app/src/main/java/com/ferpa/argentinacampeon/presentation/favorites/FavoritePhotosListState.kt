package com.ferpa.argentinacampeon.presentation.favorites

import com.ferpa.argentinacampeon.domain.model.Photo

data class FavoritePhotosListState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val error: String = ""
)
