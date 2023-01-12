package com.ferpa.argentinacampeon.presentation.photo_list

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto

data class PhotoListState(
    val isLoading: Boolean = false,
    val photos: List<PhotoDto> = emptyList(),
    val error: String = ""
)
