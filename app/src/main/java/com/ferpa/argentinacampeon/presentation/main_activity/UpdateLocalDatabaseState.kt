package com.ferpa.argentinacampeon.presentation.main_activity

data class UpdateLocalDatabaseState(
    val updateLocalState: UpdateLocalState = UpdateLocalState(isLoading = true),
    val updateLocalPhotoListState: UpdateLocalPhotoListState = UpdateLocalPhotoListState(isLoading = true),
    val updateLocalPlayerListState: UpdateLocalPlayerListState = UpdateLocalPlayerListState(isLoading = true),
    val updateLocalMatchListState: UpdateLocalMatchListState = UpdateLocalMatchListState(isLoading = true)
)
