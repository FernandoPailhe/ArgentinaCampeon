package com.ferpa.argentinacampeon.presentation.versus


import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersusViewModel @Inject constructor(
    private val postVoteUseCase: PostVoteUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val shareImageUseCase: ShareImageUseCase,
    private val getShareInfoUseCase: GetShareInfoUseCase
) : ViewModel() {

    private val _shareImageState = mutableStateOf(ShareImageState())
    val shareImageState: MutableState<ShareImageState> = _shareImageState

    init {

    }

    fun postVote(vote: Vote, isTutorial: Boolean = false) {
        viewModelScope.launch {
            postVoteUseCase(vote, isTutorial)
        }
    }

    fun switchFavorite(photoId: String) {
        viewModelScope.launch {
            switchFavoriteUseCase(photoId)
        }
    }

    fun shareImage(photo: Photo, context: Context) {
        photo.getDownloadUrl()?.let { downloadUrl ->
            downloadImageUseCase(downloadUrl, context).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _shareImageState.value = ShareImageState(image = it)
                            val text = photo.photographer?.share() + System.getProperty("line.separator") + (getShareInfoUseCase() ?: "")
                            shareImageState.value.image?.let { it1 ->
                                shareImageUseCase(
                                    it1,
                                    context,
                                    text
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _shareImageState.value = ShareImageState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _shareImageState.value = ShareImageState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun closeDialog() {
        _shareImageState.value = ShareImageState()
    }

}