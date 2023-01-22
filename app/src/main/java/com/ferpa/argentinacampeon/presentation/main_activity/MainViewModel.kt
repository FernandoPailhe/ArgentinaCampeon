package com.ferpa.argentinacampeon.presentation.main_activity

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Extensions.appVersion
import com.ferpa.argentinacampeon.common.Extensions.toPairIdList
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.domain.model.ServerInfo
import com.ferpa.argentinacampeon.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMinVersionUseCase: GetMinVersionUseCase,
    private val getForceUpdateVersionUseCase: GetForceUpdateVersionUseCase,
    private val getIsFirstTimeUseCase: GetIsFirstTimeUseCase,
    private val setFirstTimeFalseUseCase: SetFirstTimeFalseUseCase,
    private val updateLocalAppInfoUseCase: UpdateLocalAppInfoUseCase,
    private val getTutorialInfoListUseCase: GetTutorialInfoListUseCase,
    private val getWelcomeInfoListUseCase: GetWelcomeInfoListUseCase,
    private val updateLocalPhotoListUseCase: UpdateLocalPhotoListUseCase,
    private val updateLocalPlayerListUseCase: UpdateLocalPlayerListUseCase,
    private val updateLocalMatchListUseCase: UpdateLocalMatchListUseCase,
    private val getVersusListUseCase: GetVersusListUseCase,
    private val getFavoritePairListStateUseCase: GetFavoritePairListStateUseCase,
    private val getLastVersionUseCase: GetLastVersionUseCase,
    private val getUpdateVersionInfoUseCase: GetUpdateVersionInfoUseCase,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _versionOk = mutableStateOf(true)
    val versionOk: MutableState<Boolean> = _versionOk

    private val _forceUpdateVersion = mutableStateOf(ServerInfo(content = "Debe actualizar la versión"))
    val forceUpdateVersion: MutableState<ServerInfo> = _forceUpdateVersion

    private val _isFirstTime = mutableStateOf(UpdateLocalState())
    val isFirstTime: MutableState<UpdateLocalState> = _isFirstTime

    private val _tutorialInfo = mutableStateOf(InfoListState())
    val tutorialInfo: MutableState<InfoListState> = _tutorialInfo

    private val _welcomeInfo = mutableStateOf(InfoListState())
    val welcomeInfo: MutableState<InfoListState> = _welcomeInfo

    private val _updateState = mutableStateOf(UpdateLocalDatabaseState())
    val updateState: MutableState<UpdateLocalDatabaseState> = _updateState

    private val _versusListState = mutableStateOf(VersusListState(isLoading = true))
    val versusListState: State<VersusListState> = _versusListState

    private val _favoriteState = mutableStateOf(FavoritePairListState(isLoading = true))
    val favoriteState: State<FavoritePairListState> = _favoriteState

    private val _isLastVersion = mutableStateOf(true)
    val isLastVersion: MutableState<Boolean> = _isLastVersion

    private val _updateVersionInfo = mutableStateOf(ServerInfo())
    val updateVersionInfo: MutableState<ServerInfo> = _updateVersionInfo

    init {
        viewModelScope.launch {
            checkIsFirstTime()
            getVersusPairList()
            updateLocalDatabase(context)
        }
    }

    private fun checkMinVersion(context: Context){
        getMinVersionUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.apply {
                        _versionOk.value = result.data <= context.appVersion()
                        Log.d("appVersion", versionOk.value.toString())
                        if (!versionOk.value) {
                            getForceUpdateVersion()
                        } else {
                            checkLastVersion(context = context)
                        }
                    }
                }
                is Resource.Error -> {
                    _versionOk.value = true
                    Log.d("appVersion", "no data from server")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkIsFirstTime() {
        getIsFirstTimeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isFirstTime.value =
                        UpdateLocalState(succes = result.data ?: false)
                }
                is Resource.Error -> {
                    _isFirstTime.value = UpdateLocalState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _isFirstTime.value = UpdateLocalState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTutorialInfoList() {
        Log.d(TAG, "getTutorialList -> running")
        getTutorialInfoListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _tutorialInfo.value =
                        InfoListState(serverInfo = result.data ?: emptyList())
                    getFavoritePairListState()
                }
                is Resource.Error -> {
                    _tutorialInfo.value = InfoListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _tutorialInfo.value = InfoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateLocalDatabase(context: Context) {
        updateLocalAppInfoUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "LOCAL APP INFO TABLE SUCCESSFULLY")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalState = UpdateLocalState(
                            isLoading = false,
                            succes = true
                        )
                    )
                    checkMinVersion(context)
                    getTutorialInfoList()
                    updateLocalPhotoList()
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOCAL APP INFO TABLE LOADING...")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalState = UpdateLocalState(isLoading = true)
                    )
                }
                is Resource.Error -> {
                    Log.d(TAG, "LOCAL APP INFO TABLE FAIL")
                    _updateState.value = UpdateLocalDatabaseState(
                        updateLocalState = UpdateLocalState(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    )
                    updateLocalPhotoList()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateLocalPhotoList() {
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
                        getFavoritePairListState()
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

    private fun getFavoritePairListState() {
        val pairIdList = versusListState.value.photos.toPairIdList()
        getFavoritePairListStateUseCase(pairIdList).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favoriteState.value =
                        FavoritePairListState(favorites = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _favoriteState.value = FavoritePairListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _favoriteState.value = FavoritePairListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFavoritePairListStateUpdate() {
        val pairIdList = versusListState.value.photos.toPairIdList()
        getFavoritePairListStateUseCase(pairIdList).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favoriteState.value =
                        FavoritePairListState(favorites = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addNewVersusPhotos() {
        Log.d(TAG, "AddNewVersusPhotos -> running")
        Log.d(TAG, "Versus List -> ${versusListState.value.photos.size}")
        val ignoreIdsPair = Pair(
            versusListState.value.photos.last()?.first?.id ?: "",
            versusListState.value.photos.last()?.second?.id ?: ""
        )
        getVersusListUseCase(ignoreIdsPair).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val tempList =
                        MutableList<Pair<Photo, Photo>?>(versusListState.value.photos.size - 1) {
                            null
                        }
                    tempList.add(versusListState.value.photos.last())
                    result.data?.forEach {
                        tempList.add(it)
                    }
                    _versusListState.value =
                        VersusListState(photos = tempList.toList() ?: emptyList())
                    getFavoritePairListStateUpdate()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setFirstTimeFalse() {
        _isFirstTime.value = UpdateLocalState(succes = false)
        viewModelScope.launch {
            setFirstTimeFalseUseCase()
        }
    }

    private fun getForceUpdateVersion() {
        getForceUpdateVersionUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _forceUpdateVersion.value = result.data ?: ServerInfo(content = "Debe actualizar la versión")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkLastVersion(context: Context){
        getLastVersionUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.apply {
                        _isLastVersion.value = result.data <= context.appVersion()
                        Log.d("appVersion", isLastVersion.value.toString())
                        getUpdateVersionInfo()
                    }
                }
                is Resource.Error -> {
                    _isLastVersion.value = true
                    Log.d("appVersion", "no data from server")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUpdateVersionInfo() {
        getUpdateVersionInfoUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _updateVersionInfo.value = result.data ?: ServerInfo()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun dismissUpdateDialog() {
        _isLastVersion.value = true
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}

