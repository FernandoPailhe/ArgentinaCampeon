package com.ferpa.argentinacampeon.presentation.versus

import java.io.File

data class ShareImageState(
    val isLoading: Boolean = false,
    val image: File? = null,
    val error: String = ""
)
