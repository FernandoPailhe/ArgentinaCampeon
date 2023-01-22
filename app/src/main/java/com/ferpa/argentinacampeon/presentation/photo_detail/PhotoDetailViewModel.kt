package com.ferpa.argentinacampeon.presentation.photo_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Constants.PARAM_PHOTO_ID
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.GetPhotoDetailUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetPhotographerDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotographerDetailUseCase: GetPhotographerDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PhotoDetailState())
    val state: State<PhotoDetailState> = _state

    private val _phState = mutableStateOf(PhotographerDetailState())
    val phState: State<PhotographerDetailState> = _phState

    init {
        savedStateHandle.get<String>(PARAM_PHOTO_ID)?.let { photoId ->
            getPhotoDetail(photoId)
        }
    }

    private fun getPhotoDetail(photoId: String) {
        Log.d("PhotoDetailViewModel", "run get photo id: $photoId")
        getPhotoDetailUseCase(photoId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PhotoDetailState(photo = result.data)
                    state.value.photo?.photographer?.id?.let { getPhotographerDetail(it) }
                }
                is Resource.Error -> {
                    _state.value = PhotoDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PhotoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotographerDetail(photographerId: String) {
        getPhotographerDetailUseCase(photographerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _phState.value = PhotographerDetailState(photographer = result.data)
                }
                is Resource.Error -> {
                    _phState.value = PhotographerDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _phState.value = PhotographerDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}