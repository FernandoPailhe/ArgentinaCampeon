package com.ferpa.argentinacampeon.presentation.photo_list


import com.ferpa.argentinacampeon.domain.model.Photo

data class PhotoListState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val error: String = ""
)
