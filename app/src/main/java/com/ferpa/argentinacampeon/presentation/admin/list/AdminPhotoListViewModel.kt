package com.ferpa.argentinacampeon.presentation.admin.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.admin.GetPhotoDtoUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.admin.GetPhotosByStateUseCase
import com.ferpa.argentinacampeon.presentation.admin.edit.PhotoDtoEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminPhotoListViewModel @Inject constructor(
    private val getPhotosByStateUseCase: GetPhotosByStateUseCase,
    private val getPhotoDtoUseCase: GetPhotoDtoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PhotoDtoListState())
    val state: State<PhotoDtoListState> = _state

    private val _photoDtoState = mutableStateOf(PhotoDtoEditState())
    val photoDtoState: State<PhotoDtoEditState> = _photoDtoState

    private val _id = mutableStateOf("")
    val id: State<String> = _id

    private val _rarity = mutableStateOf(-3)
    val rarity: State<Int> = _rarity

    init {
        getPhotosByState()
    }

    private fun getPhotosByState() {
        getPhotosByStateUseCase(rarity.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PhotoDtoListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = PhotoDtoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PhotoDtoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotoDto() {
        getPhotoDtoUseCase(id.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoDtoState.value = PhotoDtoEditState(photo = result.data)
                }
                is Resource.Error -> {
                    _photoDtoState.value = PhotoDtoEditState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _photoDtoState.value = PhotoDtoEditState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setState(photoState: Int) {
        _rarity.value = photoState
        getPhotosByState()
    }

}