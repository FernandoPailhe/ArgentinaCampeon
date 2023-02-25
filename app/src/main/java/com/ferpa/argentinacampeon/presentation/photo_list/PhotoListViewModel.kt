package com.ferpa.argentinacampeon.presentation.photo_list

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
    private val getTagDetailUseCase: GetTagDetailUseCase,
    private val getPhotographerDetailUseCase: GetPhotographerDetailUseCase,
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
            getTagDetailById(tag)
        }
        savedStateHandle.get<String>(Constants.PARAM_PHOTOGRAPHER_ID)?.let { photographerId ->
            getPhotoListByPhotographer(photographerId)
            getPhotographerDetailById(photographerId)
        }
    }

    /**
     * Function to get the match detail by their ID provided
     *
     * @param id The ID used to get the detail
     */
    private fun getMatchDetailById(id: String){
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

    /**
     * Function to get the player detail by their player ID provided
     *
     * @param playerId The player ID used to get the detail
     */
    private fun getPlayerDetailById(playerId: String){
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

    /**
     * Function to get the tag detail by their ID provided
     *
     * @param id The ID used to get the detail
     */
    private fun getTagDetailById(id: String){
        getTagDetailUseCase(id).onEach { result ->
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

    /**
     * Function to get the photographer detail by their ID provided
     *
     * @param id The ID used to get the detail
     */
    private fun getPhotographerDetailById(id: String){
        getPhotographerDetailUseCase(id).onEach { result ->
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

    /**
     * Function to get the list of photos by match ID provided
     *
     * @param matchId The match ID used to get the photo list
     */
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

    /**
     * Function to get the list of photos by tag provided
     *
     * @param tag The tag used to get the photo list
     */
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

    /**
     * Function to get the list of photos by player ID provided
     *
     * @param playerId The player ID used to get the photo list
     */
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

    /**
     * Function to get the list of photos by photographer ID provided
     *
     * @param photographerId The photographer ID used to get the photo list
     */
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