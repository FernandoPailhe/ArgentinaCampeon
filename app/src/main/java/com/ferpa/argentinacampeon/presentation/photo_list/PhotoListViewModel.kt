package com.ferpa.argentinacampeon.presentation.photo_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosByMatchUseCase: GetPhotosByMatchUseCase,
    private val getPhotosByPlayerUseCase: GetPhotosByPlayerUseCase,
    private val getPhotosByTagUseCase: GetPhotosByTagUseCase,
    private val getPhotosByPhotographerUseCase: GetPhotosByPhotographerUseCase,
    private val getPlayerDetailUseCase: GetPlayerDetailUseCase,
    private val getMatchDetailUseCase: GetMatchDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _photoListState = mutableStateOf(PhotoListState())
    val photoListState: State<PhotoListState> = _photoListState

    private val _infoState = mutableStateOf(InfoState())
    val infoState: State<InfoState> = _infoState

    init {
        savedStateHandle.get<String>(Constants.PARAM_MATCH_ID)?.let { matchId ->
            getPhotoListByMatch(matchId)
            getMatchDetailById(matchId)
        }
        savedStateHandle.get<String>(Constants.PARAM_PLAYER_ID)?.let { playerId ->
            getPhotoListByPlayer(playerId)
            getPlayerDetailById(playerId)
        }
        savedStateHandle.get<String>(Constants.PARAM_TAG)?.let { tag ->
            getPhotoListByTag(tag)
        }
        savedStateHandle.get<String>(Constants.PARAM_PHOTOGRAPHER_ID)?.let { photographerId ->
            getPhotoListByPhotographer(photographerId)
        }

    }


    private fun getMatchDetailById(id: String){
        Log.d("getMatchDetailViewModel",id )
        getMatchDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _infoState.value = InfoState(info = result.data)
                }
                is Resource.Error -> {
                    _infoState.value = InfoState( error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _infoState.value = InfoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPlayerDetailById(playerId: String){
        Log.d("getPlayerDetailViewModel",playerId )
        getPlayerDetailUseCase(playerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _infoState.value = InfoState(info = result.data)
                }
                is Resource.Error -> {
                    _infoState.value = InfoState( error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _infoState.value = InfoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotoListByMatch(matchId: String) {
        getPhotosByMatchUseCase(matchId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoListState.value = PhotoListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _photoListState.value = PhotoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _photoListState.value = PhotoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotoListByTag(tag: String) {
        getPhotosByTagUseCase(tag).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoListState.value = PhotoListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _photoListState.value = PhotoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _photoListState.value = PhotoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotoListByPlayer(playerId: String) {
        getPhotosByPlayerUseCase(playerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoListState.value = PhotoListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _photoListState.value = PhotoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _photoListState.value = PhotoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotoListByPhotographer(photographerId: String) {
        getPhotosByPhotographerUseCase(photographerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoListState.value = PhotoListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _photoListState.value = PhotoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _photoListState.value = PhotoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}