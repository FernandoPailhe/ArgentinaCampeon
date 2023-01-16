package com.ferpa.argentinacampeon.domain.repository

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.domain.model.*
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getFirstTime(): Flow<Boolean?>

    suspend fun checkFirstTime(): Boolean

    suspend fun setFirstTime(isFirstTime: Boolean)

    suspend fun updateLocalAppInfo(): Boolean

    suspend fun updateLocalPhotoList(): Boolean

    suspend fun updateLocalPhotoVotes(remoteUpdate: String = "")

    suspend fun updateLocalPlayersList(): Boolean

    suspend fun updateLocalMatchList(): Boolean

    suspend fun getWelcomeInfo(): List<InfoFromApi?>?

    suspend fun getTutorialInfo(): List<InfoFromApi?>?

    suspend fun getAboutUsInfo(): List<InfoFromApi?>?

    suspend fun getShareInfo(): List<InfoFromApi?>?

    suspend fun getExtraInfo(): List<InfoFromApi?>?

    suspend fun insertNewPhotos(localLastInsertPhotoDate: String): Boolean

    suspend fun getTutorialVersusList(size: Int): List<Pair<Photo, Photo>>

    suspend fun getVersusList(ignorePair: Pair<String, String>): List<Pair<Photo, Photo>>

    suspend fun getFavoritesPhotos(): List<Photo>

    suspend fun getPhotosByPlayer(playerId: String): List<Photo>

    suspend fun getPhotosByMatch(matchId: String): List<Photo>

    suspend fun getPhotosByTag(tag: String): List<Photo>

    suspend fun getPhotosByPhotographer(photographerId: String): List<Photo>

    suspend fun getBestPhotos(): List<Photo>

    suspend fun getPhotoDetail(id: String): PhotoDto

    suspend fun getFavoritePairListState(pairIdList: List<Pair<String, String>>): List<Pair<Boolean,Boolean>>

    suspend fun getFavoriteListState(idList: List<String>): List<Boolean>

    suspend fun getPlayerDetail(playerId: String): Player

    suspend fun getPhotographerDetail(photographerId: String): Photographer

    suspend fun getMatchDetail(matchId: String): Match

    suspend fun getTagDetail(tagId: String): Tag

    suspend fun postVote(vote: Vote)

    suspend fun onlyLocalVote(vote: Vote)

    suspend fun postPlayer(player: Player)

    suspend fun switchFavorite(photoId: String)

}