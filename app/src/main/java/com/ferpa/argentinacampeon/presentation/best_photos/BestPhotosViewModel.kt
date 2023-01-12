package com.ferpa.argentinacampeon.presentation.best_photos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.GetBestPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BestPhotosViewModel @Inject constructor(
    private val getBestPhotosUseCase: GetBestPhotosUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(BestPhotosListState())
    val state: State<BestPhotosListState> = _state

    init {
        getBestPhotosList()
    }

    private fun getBestPhotosList() {
        getBestPhotosUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = BestPhotosListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = BestPhotosListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = BestPhotosListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}