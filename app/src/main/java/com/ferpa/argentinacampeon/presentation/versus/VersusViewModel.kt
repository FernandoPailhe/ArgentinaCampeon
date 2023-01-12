package com.ferpa.argentinacampeon.presentation.versus

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.argentinacampeon.domain.businesslogic.PostVoteUseCase
import com.ferpa.argentinacampeon.domain.businesslogic.SwitchFavoriteUseCase
import com.ferpa.argentinacampeon.domain.model.Vote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersusViewModel @Inject constructor(
    private val postVoteUseCase: PostVoteUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModel() {

    private val _favoriteIconState = mutableStateOf(Pair(false, false))
    val favoriteIconState: State<Pair<Boolean?, Boolean?>> = _favoriteIconState

    init {

    }

    fun postVote(vote: Vote) {
        viewModelScope.launch {
            postVoteUseCase(vote)
        }
    }

    fun switchFavorite(photoId: String) {
        viewModelScope.launch {
            switchFavoriteUseCase(photoId)
        }
    }

}