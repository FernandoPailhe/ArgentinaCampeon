package com.ferpa.argentinacampeon.presentation.photo_detail

import com.ferpa.argentinacampeon.domain.model.Photographer

data class PhotographerDetailState(
    val isLoading: Boolean = false,
    val photographer: Photographer? = null,
    val error: String = ""
)
