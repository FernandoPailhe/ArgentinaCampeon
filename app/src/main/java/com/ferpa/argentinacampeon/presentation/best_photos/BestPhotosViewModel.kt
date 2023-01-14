package com.ferpa.argentinacampeon.presentation.best_photos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Extensions.toPairIdList
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.GetBestPhotosUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetFavoriteListStateUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetFavoritePairListStateUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.SwitchFavoriteUseCase
import com.ferpa.argentinacampeon.presentation.main_activity.FavoritePairListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestPhotosViewModel @Inject constructor(
    private val getBestPhotosUseCase: GetBestPhotosUseCase,
    private val getFavoriteListStateUseCase: GetFavoriteListStateUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(BestPhotosListState())
    val state: State<BestPhotosListState> = _state

    private val _favoriteState = mutableStateOf(FavoriteListState())
    val favoriteState: State<FavoriteListState> = _favoriteState

    init {
        getBestPhotosList()
    }

    private fun getBestPhotosList() {
        getBestPhotosUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = BestPhotosListState(photos = result.data ?: emptyList())
                    getFavoriteListState()
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

    private fun getFavoriteListState() {
        val idList = state.value.photos.map { it.id }
        getFavoriteListStateUseCase(idList).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favoriteState.value =
                        FavoriteListState(favorites = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _favoriteState.value = FavoriteListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _favoriteState.value = FavoriteListState(isLoading = true)
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