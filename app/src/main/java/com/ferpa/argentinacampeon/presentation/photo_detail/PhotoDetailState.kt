package com.ferpa.argentinacampeon.presentation.photo_detail

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photo: PhotoDto? = null,
    val error: String = ""
)
