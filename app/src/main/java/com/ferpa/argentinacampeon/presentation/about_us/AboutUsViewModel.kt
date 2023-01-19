package com.ferpa.argentinacampeon.presentation.about_us

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.presentation.main_activity.InfoListState
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(
    private val getAboutUsInfoListUseCase: GetAboutUsInfoListUseCase,
) : ViewModel() {

    private val _aboutUsInfo = mutableStateOf(InfoListState())
    val aboutUsInfo: MutableState<InfoListState> = _aboutUsInfo

    init {
        getAboutUsInfoList()
    }

    private fun getAboutUsInfoList() {
        Log.d(MainViewModel.TAG, "getAboutUsInfoList -> running")
        getAboutUsInfoListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _aboutUsInfo.value =
                        InfoListState(serverInfo = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _aboutUsInfo.value = InfoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _aboutUsInfo.value = InfoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}