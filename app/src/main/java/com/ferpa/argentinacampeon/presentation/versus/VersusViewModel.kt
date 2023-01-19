package com.ferpa.argentinacampeon.presentation.versus


import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.common.Resource
import com.ferpa.argentinacampeon.domain.businesslogic.DownloadImageUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.PostVoteUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.ShareImageUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.SwitchFavoriteUseCase
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.Vote
import com.ferpa.argentinacampeon.domain.model.getDownloadUrl
import com.ferpa.argentinacampeon.presentation.main_activity.VersusListState
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
    private val shareImageUseCase: ShareImageUseCase
) : ViewModel() {

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

    fun shareImage(downloadUrl: String, context: Context) {
        downloadImageUseCase(downloadUrl, context).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { shareImageUseCase(it, context) }
                }
            }
        }.launchIn(viewModelScope)
    }

}