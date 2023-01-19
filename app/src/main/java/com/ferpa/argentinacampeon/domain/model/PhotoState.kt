package com.ferpa.argentinacampeon.domain.model

sealed class PhotoState(val rarity: Int) {
    object AvailableState: PhotoState(0)
    object HideState:PhotoState (-1)
    object ReadyToProductionState: PhotoState (-2)
    object IncompleteState: PhotoState (-3)
    object DeletedState: PhotoState(-4)
}
