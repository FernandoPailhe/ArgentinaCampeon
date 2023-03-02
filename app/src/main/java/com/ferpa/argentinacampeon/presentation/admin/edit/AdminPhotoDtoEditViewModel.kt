package com.ferpa.argentinacampeon.presentation.admin.edit

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.data.remote.dto.addPlayer
import com.ferpa.argentinacampeon.data.remote.dto.removePlayer
import com.ferpa.argentinacampeon.domain.businesslogic.GetAllPlayersUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetMatchesUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetPhotographersUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetPlayersUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.admin.FullUpdatePhotoUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.admin.GetPhotoDtoUseCase
import com.ferpa.argentinacampeon.domain.model.MatchTitle
import com.ferpa.argentinacampeon.domain.model.PhotographerTitle
import com.ferpa.argentinacampeon.domain.model.PlayerTitle
import com.ferpa.argentinacampeon.presentation.select_list.SelectListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminPhotoDtoEditViewModel @Inject constructor(
    private val getPhotoDtoUseCase: GetPhotoDtoUseCase,
    private val fullUpdatePhotoUseCase: FullUpdatePhotoUseCase,
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val getMatchesUseCase: GetMatchesUseCase,
    private val getPhotographersUseCase: GetPhotographersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _photoDtoState = mutableStateOf(PhotoDtoEditState())
    val photoDtoState: State<PhotoDtoEditState> = _photoDtoState

    private val _playersList = mutableStateOf(AllPlayersListState())
    val playersList: State<AllPlayersListState> = _playersList

    private val _matchesListState = mutableStateOf(SelectListState())
    val matchesListState: State<SelectListState> = _matchesListState

    private val _photographersListState = mutableStateOf(SelectListState())
    val photographersListState: State<SelectListState> = _photographersListState

    private val _newPhotoDto = mutableStateOf(photoDtoState.value.photo)
    val newPhotoDto: State<PhotoDto?> = _newPhotoDto

    init {
        savedStateHandle.get<String>(Constants.PARAM_PHOTO_ID)?.let { photoId ->
            getPhotoDto(photoId)
        }
        viewModelScope.launch {
            getMatches()
            getPlayers()
            getPhotographers()
        }
    }

    private fun getPhotoDto(id: String) {
        getPhotoDtoUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _photoDtoState.value = PhotoDtoEditState(photo = result.data)
                    _newPhotoDto.value = result.data
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

    fun fullUpdate() {
        viewModelScope.launch {
            if (newPhotoDto.value != null) {
                fullUpdatePhotoUseCase(newPhotoDto.value!!)
            }
        }
    }

    private fun getPhotographers() {
        viewModelScope.launch {
            getPhotographersUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _photographersListState.value = SelectListState(photographers = result.data)
                    }
                    is Resource.Error -> {
                        _photographersListState.value =
                            SelectListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }
                    is Resource.Loading -> {
                        _photographersListState.value = SelectListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getMatches() {
        viewModelScope.launch {
            getMatchesUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _matchesListState.value = SelectListState(matches = result.data)
                    }
                    is Resource.Error -> {
                        _matchesListState.value =
                            SelectListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }
                    is Resource.Loading -> {
                        _matchesListState.value = SelectListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getPlayers() {
        Log.d("AdminPLayers", "running")
        getAllPlayersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _playersList.value = AllPlayersListState(players = result.data)
                }
                is Resource.Error -> {
                    _playersList.value =
                        AllPlayersListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _playersList.value = AllPlayersListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addPlayer(player: PlayerTitle) {
        Log.d("player", player.displayName.toString())
        _newPhotoDto.value = newPhotoDto.value?.addPlayer(player)
        _photoDtoState.value = PhotoDtoEditState(photo = newPhotoDto.value)
        Log.d("player", newPhotoDto.value?.players?.size.toString())
    }

    fun removePlayer(player: PlayerTitle) {
        _newPhotoDto.value = newPhotoDto.value?.removePlayer(player)
        _photoDtoState.value = PhotoDtoEditState(photo = newPhotoDto.value)
    }

    fun setState(state: Int) {
        _newPhotoDto.value = newPhotoDto.value?.copy(rarity = state)
        _photoDtoState.value = PhotoDtoEditState(photo = newPhotoDto.value)
    }

    fun setVotesAndVersus(votes: Long, versus: Long){
        _newPhotoDto.value = newPhotoDto.value?.copy(votes = votes, versus = versus)
        _photoDtoState.value = PhotoDtoEditState(photo = newPhotoDto.value)
    }

    fun setMatch(matchTitle: MatchTitle) {
        _newPhotoDto.value = newPhotoDto.value?.copy(match = matchTitle)
        _photoDtoState.value = PhotoDtoEditState(photo = newPhotoDto.value)
    }

    fun setPhotographer(photographerTitle: PhotographerTitle) {
        _newPhotoDto.value = newPhotoDto.value?.copy(photographer = photographerTitle )
        _photoDtoState.value = PhotoDtoEditState(photo = newPhotoDto.value)
    }
}