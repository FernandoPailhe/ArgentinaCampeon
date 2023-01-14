package com.ferpa.argentinacampeon.presentation.best_photos

data class FavoriteListState(
    val isLoading: Boolean = false,
    val favorites: List<Boolean> = emptyList(),
    val error: String = ""
)
