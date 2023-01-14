package com.ferpa.argentinacampeon.presentation.main_activity

data class FavoritePairListState(
    val isLoading: Boolean = false,
    val favorites: List<Pair<Boolean, Boolean>> = emptyList(),
    val error: String = ""
)
