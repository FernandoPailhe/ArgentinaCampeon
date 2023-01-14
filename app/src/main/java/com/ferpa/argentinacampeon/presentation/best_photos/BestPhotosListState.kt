package com.ferpa.argentinacampeon.presentation.best_photos

import com.ferpa.argentinacampeon.domain.model.Photo

data class BestPhotosListState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val error: String = ""
)
