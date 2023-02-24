package com.ferpa.argentinacampeon.presentation.versus


import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Extensions.appVersion
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.*
import com.ferpa.argentinacampeon.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val getShareInfoUseCase: GetShareInfoUseCase,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _shareImageState = mutableStateOf(ShareImageState())
    val shareImageState: MutableState<ShareImageState> = _shareImageState

    init {

    }

    /**
     * postVote()
     *
     * @param vote {Vote} The Vote to be used.
     * @param isTutorial {Boolean} Optional parameter.
     *
     * Launches a postVoteUseCase() with the given Vote and the value of isTutorial in an Android ViewModelScope.
     */
    fun postVote(vote: Vote, isTutorial: Boolean = false) {
        viewModelScope.launch {
            postVoteUseCase(vote, isTutorial)
        }
    }

    /**
     * switchFavorite()
     *
     * @param photoId {String} The Id of the Photo.
     *
     * Launches a switchFavoriteUseCase() with the given photoId in an Android ViewModelScope.
     */
    fun switchFavorite(photoId: String) {
        viewModelScope.launch {
            switchFavoriteUseCase(photoId)
        }
    }

    /**
     * shareImage()
     *
     * @param photo {Photo} The Photo to be shared.
     * @param context {Context} The Context in which the Photo is shared.
     *
     * Uses the given photo's getDownloadUrl() to launch a downloadImageUseCase() in an Android ViewModelScope. The
     * _shareImageState is set to a new ShareImageState() with the data from the result of the downloadImageUseCase().
     * If the download was successful, the _shareImageState is set with the data from the result. Then the
     * shareImageState is set with the image and a string is created by photograper's share() and a message from the
     * given context. Finally, shareImageUseCase() is launched with the given image, context and text.
     */
    fun shareImage(photo: Photo, context: Context) {
        photo.getDownloadUrl()?.let { downloadUrl ->
            downloadImageUseCase(downloadUrl, context).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _shareImageState.value = ShareImageState(image = it)
                            val text = photo.photographer?.share() + System.getProperty("line.separator") + context.getString(R.string.share)
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

    /**
     * closeDialog()
     *
     * Resets the _shareImageState to ShareImageState().
     */
    fun closeDialog() {
        _shareImageState.value = ShareImageState()
    }

}