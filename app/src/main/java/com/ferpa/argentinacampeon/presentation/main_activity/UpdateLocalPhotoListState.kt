package com.ferpa.argentinacampeon.presentation.main_activity

data class UpdateLocalPhotoListState(
    val isLoading: Boolean = false,
    val succes: Boolean = false,
    val error: String = ""
)
