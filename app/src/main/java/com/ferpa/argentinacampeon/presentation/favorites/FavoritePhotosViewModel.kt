package com.ferpa.argentinacampeon.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.GetFavoritesPhotosUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.SwitchFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosViewModel @Inject constructor(
    private val getFavoritesPhotosUseCase: GetFavoritesPhotosUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoritePhotosListState())
    val state: State<FavoritePhotosListState> = _state

    init {
        getFavoritePhotoList()
    }

    private fun getFavoritePhotoList() {
        getFavoritesPhotosUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FavoritePhotosListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FavoritePhotosListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = FavoritePhotosListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun switchFavorite(photoId: String) {
        viewModelScope.launch {
            switchFavoriteUseCase(photoId)
        }
    }

}