package com.ferpa.argentinacampeon.presentation.favorites

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.DownloadImageUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.GetFavoritesPhotosUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.ShareImageUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.SwitchFavoriteUseCase
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.getDownloadUrl
import com.ferpa.argentinacampeon.domain.model.share
import com.ferpa.argentinacampeon.presentation.versus.ShareImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosViewModel @Inject constructor(
    private val getFavoritesPhotosUseCase: GetFavoritesPhotosUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val shareImageUseCase: ShareImageUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FavoritePhotosListState())
    val state: State<FavoritePhotosListState> = _state

    private val _shareImageState = mutableStateOf(ShareImageState())
    val shareImageState: MutableState<ShareImageState> = _shareImageState

    init {
        getFavoritePhotoList()
    }

    private fun getFavoritePhotoList() {
        getFavoritesPhotosUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FavoritePhotosListState(photos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FavoritePhotosListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = FavoritePhotosListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
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
                            val text = photo.photographer?.share() + System.getProperty("line.separator") + context.getString(
                                R.string.share)
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

}