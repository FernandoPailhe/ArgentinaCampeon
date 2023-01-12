package com.ferpa.argentinacampeon.presentation.best_photos

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto

data class BestPhotosListState(
    val isLoading: Boolean = false,
    val photos: List<PhotoDto> = emptyList(),
    val error: String = ""
)
