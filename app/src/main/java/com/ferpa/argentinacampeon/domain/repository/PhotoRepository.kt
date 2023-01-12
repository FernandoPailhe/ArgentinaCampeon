package com.ferpa.argentinacampeon.domain.repository

import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.domain.model.*

interface PhotoRepository {

    suspend fun updateLocalPhotoList(): Boolean

    suspend fun updateLocalPhotoVotes(remoteUpdate: String = "")

    suspend fun updateLocalPlayersList(): Boolean

    suspend fun updateLocalMatchList(): Boolean

    suspend fun insertNewPhotos(localLastInsertPhotoDate: String): Boolean

    suspend fun getVersusList(): List<Pair<Photo, Photo>>

//    suspend fun getVersusPair(): Flow<Pair<LocalPhoto, LocalPhoto>>

    suspend fun getFavoritesPhotos(): List<Photo>

    suspend fun getPhotosByPlayer(playerId: String): List<PhotoDto>

    suspend fun getPhotosByMatch(matchId: String): List<PhotoDto>

    suspend fun getPhotosByTag(tag: String): List<PhotoDto>

    suspend fun getPhotosByPhotographer(photographerId: String): List<PhotoDto>

    suspend fun getBestPhotos(): List<PhotoDto>

    suspend fun getPhotoDetail(id: String): PhotoDto

    suspend fun getFavoriteStateById(id: String): Boolean

    suspend fun getPlayerDetail(playerId: String): Player

    suspend fun getPhotographerDetail(photographerId: String): Photographer

    suspend fun getMatchDetail(matchId: String): Match

    suspend fun postVote(vote: Vote)

    suspend fun postPlayer(player: Player)

    suspend fun switchFavorite(photoId: String)

}