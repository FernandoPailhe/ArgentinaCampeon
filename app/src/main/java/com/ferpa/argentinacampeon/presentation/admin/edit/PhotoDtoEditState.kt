package com.ferpa.argentinacampeon.presentation.admin.edit

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto

data class PhotoDtoEditState(
    val isLoading: Boolean = false,
    val photo: PhotoDto? = null,
    val error: String = ""
)
