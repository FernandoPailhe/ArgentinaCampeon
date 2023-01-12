package com.ferpa.argentinacampeon.presentation.insertData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.domain.businesslogic.PostNewPlayerUseCase
import com.ferpa.argentinacampeon.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertDataViewModel @Inject constructor(
    private val postNewPlayerUseCase: PostNewPlayerUseCase,
): ViewModel() {

    fun insertLocalPlayer(player: Player) {
        viewModelScope.launch {
            postNewPlayerUseCase(player)
        }
    }
}