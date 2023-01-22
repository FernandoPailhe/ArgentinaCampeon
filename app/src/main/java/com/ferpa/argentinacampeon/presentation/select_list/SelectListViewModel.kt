package com.ferpa.argentinacampeon.presentation.select_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.MATCHES
import com.ferpa.argentinacampeon.common.Constants.PHOTOGRAPHERS
import com.ferpa.argentinacampeon.common.Constants.PLAYERS
import com.ferpa.argentinacampeon.common.Constants.TAGS
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Route
import javax.inject.Inject

@HiltViewModel
class SelectListViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getPhotographersUseCase: GetPhotographersUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val updateLocalPlayerPhotoDataUseCase: UpdateLocalPlayerPhotoDataUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectListState = mutableStateOf(SelectListState())
    val selectListState: State<SelectListState> = _selectListState

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    init {
        getMatches()
        viewModelScope.launch {
            updateLocalPlayerPhotoDataUseCase()
        }
    }

    private fun getMatches() {
        viewModelScope.launch {
            getMatchesUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _selectListState.value = SelectListState(matches = result.data)
                    }
                    is Resource.Error -> {
                        _selectListState.value =
                            SelectListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }
                    is Resource.Loading -> {
                        _selectListState.value = SelectListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getPlayers() {
        getPlayersUseCase(search.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _selectListState.value = SelectListState(players = result.data)
                }
                is Resource.Error -> {
                    _selectListState.value =
                        SelectListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _selectListState.value = SelectListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPhotographers() {
        getPhotographersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _selectListState.value = SelectListState(photographers = result.data)
                }
                is Resource.Error -> {
                    _selectListState.value =
                        SelectListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _selectListState.value = SelectListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTags() {
        getTagsUseCase(search.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _selectListState.value = SelectListState(tags = result.data)
                }
                is Resource.Error -> {
                    _selectListState.value =
                        SelectListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _selectListState.value = SelectListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setPage(route: String) {
        when (route) {
            Screen.SelectListScreenRoute.createRoute(MATCHES) -> getMatches()
            Screen.SelectListScreenRoute.createRoute(PLAYERS) -> getPlayers()
            Screen.SelectListScreenRoute.createRoute(PHOTOGRAPHERS) -> getPhotographers()
            Screen.SelectListScreenRoute.createRoute(TAGS) -> getTags()
        }
    }

}