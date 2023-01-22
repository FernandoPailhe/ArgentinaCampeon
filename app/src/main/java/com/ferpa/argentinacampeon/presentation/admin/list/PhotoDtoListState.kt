package com.ferpa.argentinacampeon.presentation.admin.list

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.domain.model.Photo

data class PhotoDtoListState(
    val isLoading: Boolean = false,
    val photos: List<PhotoDto> = emptyList(),
    val error: String = ""
)
