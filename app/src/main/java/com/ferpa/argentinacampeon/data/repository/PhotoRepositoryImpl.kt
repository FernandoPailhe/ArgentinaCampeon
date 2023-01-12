package com.ferpa.argentinacampeon.data.repository


import android.util.Log
import com.ferpa.argentinacampeon.common.Extensions.toHiddenPhotoList
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService
import com.ferpa.argentinacampeon.data.PhotoDao
import com.ferpa.argentinacampeon.data.remote.dto.*
import com.ferpa.argentinacampeon.domain.model.*
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime


class PhotoRepositoryImpl(
    private val photoDao: PhotoDao,
    private val photoSource: ArgentinaCampeonService
) : PhotoRepository {

    override suspend fun updateLocalPhotoList(): Boolean {

        return if (photoDao.getAllLocalPhoto().first().isEmpty()) {
            return try {
                photoSource.getNewPhotos("").forEach {
                    photoDao.insertLocalPhoto(it.toLocalPhoto())
                    Log.d("updateLocalPhotoList", "Insert -> ${it.photoUrl}")
                }
                true
            } catch (e: Exception) {
                Log.d("updateLocalPhotoList", e.toString())
                false
            }
        } else {
            val lastRemoteUpdates = photoSource.getLastUpdatesDates()
            updateLocalPhotoVotes(lastRemoteUpdates.lastVotesUpdate.toString())
            val localLastInsertPhotoDate =
                LocalDateTime.parse(photoDao.getLastInsertPhotoDate().first())
            val remoteLastInsertPhotoDate = LocalDateTime.parse(lastRemoteUpdates.lastInsertDate)
            Log.d("updateLocalPhotoList", "Local -> $localLastInsertPhotoDate")
            Log.d("updateLocalPhotoList", "Remote -> $remoteLastInsertPhotoDate")
            if (remoteLastInsertPhotoDate.isAfter(localLastInsertPhotoDate)) {
                Log.d("updateLocalPhotoList", "False")
                if (insertNewPhotos(localLastInsertPhotoDate.toString())) {
                    Log.d("updateLocalPhotoList", "True")
                    true
                } else false
            } else {
                Log.d("updateLocalPhotoList", "True")
                true
            }
        }
    }

    override suspend fun updateLocalPhotoVotes(remoteUpdate: String){

        val localLastVotesUpdate = LocalDateTime.parse(photoDao.getLastVotesUpdate().first())
        val remoteLastVotesUpdate = if (remoteUpdate.isEmpty()) {
            LocalDateTime.parse(photoSource.getLastUpdatesDates().lastVotesUpdate)
        } else {
            LocalDateTime.parse(remoteUpdate)
        }
        if (remoteLastVotesUpdate.isAfter(localLastVotesUpdate)) {
            photoSource.getVotesUpdate(localLastVotesUpdate.toString()).forEach {
                updateLocalPhotoVotesDb(it)
                Log.d("updateLocalPhotoVotes", "True")
            }
        } else {
            Log.d("updateLocalPhotoVotes", "True")
            true
        }
    }

    override suspend fun updateLocalPlayersList(): Boolean {
        return try {
            val lastUpdate = photoDao.getLastPlayerUpdateDate().first() ?: ""
            Log.d("updateLocalPlayerList", "LastUpdate -> $lastUpdate")
            val updatedResponse = photoSource.getPlayersList(lastUpdate)
            if (updatedResponse.isNullOrEmpty()) return true
            updatedResponse.forEach { player ->
                photoDao.insertPlayer(
                    player.toLocalPlayer(
                        photoDao.getPlayerById(player.id).first()
                    )
                )
                Log.d("updateLocalPlayerList", "Insert -> ${player.displayName}")
            }
            true
        } catch (e: Exception) {
            Log.d("updateLocalPlayerList", e.toString())
            false
        }
    }

    override suspend fun updateLocalMatchList(): Boolean {
        return try {
            val lastUpdate = photoDao.getLastMatchUpdateDate().first() ?: ""
            Log.d("updateLocalMatchList", "LastUpdate -> $lastUpdate")
            val updatedResponse = photoSource.getMatchList(lastUpdate)
            if (updatedResponse.isNullOrEmpty()) return true
            updatedResponse.forEach { match ->
                photoDao.insertMatch(match)
                Log.d("updateLocalMatchList", "Insert -> ${match.tournamentInstance}")
            }
            true
        } catch (e: Exception) {
            Log.d("updateLocalMatchList", e.toString())
            false
        }
    }

    override suspend fun insertNewPhotos(localLastInsertPhotoDate: String): Boolean {
        return try {
            photoSource.getNewPhotos(localLastInsertPhotoDate).forEach {
                photoDao.insertLocalPhoto(it.toLocalPhoto())
                Log.d("updateLocalPhotoList", it.photoUrl.toString())
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getVersusList(): List<Pair<Photo, Photo>> {

        val versusPhotos = photoDao.getVersusPhoto().first().sortedWith(compareBy(
            { it.localVersus }, { it.rank }
        ))

        val firstList = versusPhotos.subList(0, versusPhotos.size / 2).shuffled()
        val secondList = versusPhotos.subList(versusPhotos.size / 2, versusPhotos.size).shuffled()
        var randomPair = firstList.mapIndexed { index, localPhotoTitle ->
            Pair(localPhotoTitle, secondList[index])
        }.filterNot {
            it.first.versusIds.contains(it.second.id)
        }

        if (randomPair.isEmpty()) {
            val newVersusList = versusPhotos.shuffled()
            val firstListSecondChance = newVersusList.subList(0, versusPhotos.size / 2).shuffled()
            val secondListSecondChance =
                newVersusList.subList(versusPhotos.size / 2, versusPhotos.size).shuffled()
            randomPair = firstListSecondChance.mapIndexed { index, localPhotoTitle ->
                Pair(localPhotoTitle, secondListSecondChance[index])
            }.filterNot {
                it.first.versusIds.contains(it.second.id)
            }
        }

        return randomPair
    }

    override suspend fun getFavoritesPhotos(): List<Photo> {
        return emptyList()
    }

    override suspend fun getPhotosByPlayer(playerId: String): List<PhotoDto> {
        return photoSource.getPhotosByPlayer(playerId)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotosByMatch(matchId: String): List<PhotoDto> {
        return photoSource.getPhotosByMatch(matchId)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotosByTag(tag: String): List<PhotoDto> {
        return photoSource.getPhotosByTag(tag)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotosByPhotographer(photographerId: String): List<PhotoDto> {
        return photoSource.getPhotosByPhotographer(photographerId)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getBestPhotos(): List<PhotoDto> {
        return photoSource.getBestPhotos().toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotoDetail(id: String): PhotoDto {
        val remotePhoto = photoSource.getPhotoById(id)
        updateLocalPhotoDetail(remotePhoto)
        return remotePhoto
    }

    override suspend fun getFavoriteStateById(id: String): Boolean {
        return false
    }

    override suspend fun getPlayerDetail(playerId: String): Player {
        return photoDao.getPlayerById(playerId).first()
    }

    override suspend fun getPhotographerDetail(photographerId: String): Photographer {
        return photoSource.getPhotographer(photographerId)
    }

    override suspend fun getMatchDetail(matchId: String): Match {
        return photoDao.getMatchById(matchId).first()
    }

    override suspend fun postVote(vote: Vote) {
        photoSource.postVote(vote.toVoteDto())
        photoDao.updateLocalPhoto(vote.photoWin.updateVoteWin(versusId = vote.photoLost.id))
        photoDao.updateLocalPhoto(vote.photoLost.updateVoteLost(versusId = vote.photoWin.id))
        updatePlayersLocalVotes(vote)
    }

    override suspend fun postPlayer(player: Player) {
        val response = photoSource.postPlayer(player.toPlayerDto())
        Log.d("postPlayer", response.code().toString())
        Log.d("postPlayer", response.message())
        Log.d("postPlayer", response.body() as String)
    }

    override suspend fun switchFavorite(photoId: String) {

    }

    private suspend fun updatePlayersLocalVotes(vote: Vote) {
        vote.photoWin.players?.forEach {
            val oldLocalPlayer = it?.let { it1 -> photoDao.getPlayerById(it1.id).first() }
            if (oldLocalPlayer != null) {
                photoDao.updateLocalPlayer(oldLocalPlayer.updateVoteWin())
            }
        }
        vote.photoLost.players?.forEach {
            val oldLocalPlayer = it?.let { it1 -> photoDao.getPlayerById(it1.id).first() }
            if (oldLocalPlayer != null) {
                photoDao.updateLocalPlayer(oldLocalPlayer.updateVoteLost())
            }
        }
    }

    private suspend fun updateLocalPhotoVotesDb(rankUpdateDto: RankUpdateDto) {
        rankUpdateDto.photoId?.let { photoId ->
            rankUpdateDto.rank?.let { newRank ->
                photoDao.getLocalPhotoById(photoId).first().updateRank(
                    newRank
                )
            }?.let { localPhoto ->
                photoDao.updateLocalPhoto(localPhoto)
            }
        }
    }

    private suspend fun updateLocalPhotoDetail(remotePhoto: PhotoDto) {
        try {
            val localPhoto = photoDao.getLocalPhotoById(remotePhoto.id).first()
            if (LocalDateTime.parse(remotePhoto.lastUpdate).isAfter(LocalDateTime.parse(localPhoto.lastUpdate))){
                photoDao.updateLocalPhoto(remotePhoto.toExistingLocalPhoto(localPhoto))
                Log.d("updateLocalPhotoDetail", remotePhoto.photoUrl.toString())
            } else {
                Log.d("updateLocalPhotoDetail", "Photo is at to date")
            }
        } catch (e: Exception) {
            Log.e("updateLocalPhotoDetail", e.localizedMessage ?: "Unknown error")
        }

    }

}