package com.ferpa.argentinacampeon.presentation.photo_list


data class InfoState(
    val isLoading: Boolean = false,
    val info: Any? = null,
    val error: String = ""
)
