package com.ferpa.argentinacampeon.presentation.tutorial

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.presentation.main_activity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val getTutorialVersusListUseCase: GetTutorialVersusListUseCase,
) : ViewModel() {

    private val _versusListState = mutableStateOf(VersusListState(isLoading = true))
    val versusListState: State<VersusListState> = _versusListState

    fun getTutorialPairList() {
        Log.d(TAG, "getTutorialPairList -> running")
        if (versusListState.value.photos.size <= 1) {
            getTutorialVersusListUseCase().onEach { result ->
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

    companion object {
        const val TAG = "TutorialViewModel"
    }
}

