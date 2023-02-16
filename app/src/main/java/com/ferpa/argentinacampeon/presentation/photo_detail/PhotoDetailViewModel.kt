package com.ferpa.argentinacampeon.presentation.photo_detail

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Constants.PARAM_PHOTO_ID
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.getDownloadUrl
import com.ferpa.argentinacampeon.domain.model.share
import com.ferpa.argentinacampeon.presentation.versus.ShareImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotographerDetailUseCase: GetPhotographerDetailUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val shareImageUseCase: ShareImageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PhotoDetailState())
    val state: State<PhotoDetailState> = _state

    private val _phState = mutableStateOf(PhotographerDetailState())
    val phState: State<PhotographerDetailState> = _phState

    private val _shareImageState = mutableStateOf(ShareImageState())
    val shareImageState: MutableState<ShareImageState> = _shareImageState

    init {
        savedStateHandle.get<String>(PARAM_PHOTO_ID)?.let { photoId ->
            getPhotoDetail(photoId)
        }
    }

    /**
     * Retrieves detailed information for a specified photo.
     *
     * @param photoId the ID of the photo to retrieve information for
     */
    private fun getPhotoDetail(photoId: String) {
        getPhotoDetailUseCase(photoId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PhotoDetailState(photo = result.data)
                    state.value.photo?.photographer?.id?.let { getPhotographerDetail(it) }
                }
                is Resource.Error -> {
                    _state.value = PhotoDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PhotoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Retrieves detailed information for a specified photographer.
     *
     * @param photographerId The ID of the photographer to retrieve information for.
     */
    private fun getPhotographerDetail(photographerId: String) {
        getPhotographerDetailUseCase(photographerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _phState.value = PhotographerDetailState(photographer = result.data)
                }
                is Resource.Error -> {
                    _phState.value = PhotographerDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _phState.value = PhotographerDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    /**
     * Shares the given photo with the provided context.
     *
     * The function sharing a Photo object as an image via a sharing Intent in Android. It takes a Photo object and a Context as parameters, and then it calls the getDownloadUrl() function of the Photo object to obtain the URL of the image to be shared. The function then uses the downloadImageUseCase to download the image from the URL and create a Bitmap object that can be shared.
     * If the download is successful, the function creates a ShareImageState object and sets the image property to the downloaded Bitmap object. Then, the function creates a text message to accompany the shared image, including the photographer's name and the app name. Finally, the function calls the shareImageUseCase to share the image and the text message via an Android sharing Intent.
     * If the download fails or an error occurs, the function sets the error property of the ShareImageState object to the error message.
     *
     * @param photo The photo to share.
     * @param context The context to share the photo with.
     */
    fun shareImage(photo: Photo, context: Context) {
        photo.getDownloadUrl()?.let { downloadUrl ->
            downloadImageUseCase(downloadUrl, context).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _shareImageState.value = ShareImageState(image = it)
                            val text =
                                photo.photographer?.share() + System.getProperty("line.separator") + context.getString(
                                    R.string.share
                                )
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