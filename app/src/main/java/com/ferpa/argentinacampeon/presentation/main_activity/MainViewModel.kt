package com.ferpa.argentinacampeon.presentation.main_activity

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateLocalPhotoListUseCase: UpdateLocalPhotoListUseCase,
    private val updateLocalPlayerListUseCase: UpdateLocalPlayerListUseCase,
    private val updateLocalMatchListUseCase: UpdateLocalMatchListUseCase,
    private val getVersusListUseCase: GetVersusListUseCase,
) : ViewModel() {

    private val _updateState = mutableStateOf(UpdateLocalDatabaseState())
    val updateState: MutableState<UpdateLocalDatabaseState> = _updateState

    private val _versusListState = mutableStateOf(VersusListState(isLoading = true))
    val versusListState: State<VersusListState> = _versusListState

    private val _versusListPositionState = mutableStateOf(0)
    val versusListPositionState: State<Int> = _versusListPositionState

    init {
        viewModelScope.launch {
            getVersusPairList()
            updateLocalDatabase()
        }
    }

    private fun updateLocalDatabase() {
        updateLocalPhotoListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "LOCAL PHOTO TABLE SUCCESSFULLY")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = UpdateLocalPhotoListState(
                            isLoading = false,
                            succes = true
                        )
                    )
                    if (versusListState.value.photos.isEmpty()) getVersusPairList()
                    updateLocalPlayerList()
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOCAL PHOTO TABLE LOADING...")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = UpdateLocalPhotoListState(isLoading = true)
                    )
                }
                is Resource.Error -> {
                    Log.d(TAG, "LOCAL PHOTO TABLE FAIL")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = UpdateLocalPhotoListState(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    )
                    updateLocalPlayerList()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateLocalPlayerList() {
        updateLocalPlayerListUseCase().onEach { result ->
            Log.d(TAG, result.toString())
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "LOCAL PLAYER TABLE SUCCESSFULLY UPDATE UPDATE")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = updateState.value.updateLocalPhotoListState,
                        updateLocalPlayerListState = UpdateLocalPlayerListState(
                            isLoading = false,
                            succes = true
                        ),
                    )
                    updateLocalMatchList()
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOCAL PLAYER TABLE LOADING...")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = updateState.value.updateLocalPhotoListState,
                        updateLocalPlayerListState = UpdateLocalPlayerListState(isLoading = true),
                    )
                }
                is Resource.Error -> {
                    Log.d(TAG, "LOCAL PLAYER TABLE FAIL")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = updateState.value.updateLocalPhotoListState,
                        updateLocalPlayerListState = UpdateLocalPlayerListState(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    )
                    updateLocalMatchList()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateLocalMatchList() {
        updateLocalMatchListUseCase().onEach { result ->
            Log.d(TAG, result.toString())
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "LOCAL MATCH TABLE SUCCESSFULLY UPDATE")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = updateState.value.updateLocalPhotoListState,
                        updateLocalPlayerListState = updateState.value.updateLocalPlayerListState,
                        updateLocalMatchListState = UpdateLocalMatchListState(
                            isLoading = false,
                            succes = true
                        ),
                    )
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOCAL MATCH TABLE LOADING...")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = updateState.value.updateLocalPhotoListState,
                        updateLocalPlayerListState = updateState.value.updateLocalPlayerListState,
                        updateLocalMatchListState = UpdateLocalMatchListState(isLoading = true),
                    )
                }
                is Resource.Error -> {
                    Log.d(TAG, "LOCAL MATCH TABLE FAIL")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalPhotoListState = updateState.value.updateLocalPhotoListState,
                        updateLocalPlayerListState = updateState.value.updateLocalPlayerListState,
                        updateLocalMatchListState = UpdateLocalMatchListState(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getVersusPairList() {
        Log.d(TAG, "GetVersusPairList -> running")
        if (versusListState.value.photos.size <= 1) {
            getVersusListUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _versusListState.value =
                            VersusListState(photos = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _versusListState.value = VersusListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _versusListState.value = VersusListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun addNewVersusPhotos(position: Int = 0) {
        Log.d(TAG, "AddNewVersusPhotos -> running")
        Log.d(TAG, "Versus List -> ${versusListState.value.photos.size}")
        getVersusListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val tempList = MutableList<Pair<Photo, Photo>?> (versusListState.value.photos.size - 1){
                        null
                    }
                    tempList.add(versusListState.value.photos.last())
                    result.data?.forEach {
                        tempList.add(it)
                    }

                    _versusListState.value =
                        VersusListState(photos = tempList.toList() ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}

