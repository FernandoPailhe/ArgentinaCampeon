package com.ferpa.argentinacampeon.data.repository


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.DATA_STORE_NAME
import com.ferpa.argentinacampeon.common.Extensions.toHiddenPhotoList
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService
import com.ferpa.argentinacampeon.data.local.PhotoDao
import com.ferpa.argentinacampeon.data.remote.dto.*
import com.ferpa.argentinacampeon.domain.model.*
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

@Suppress("USELESS_ELVIS")
class PhotoRepositoryImpl(
    private val photoDao: PhotoDao,
    private val photoSource: ArgentinaCampeonService,
    private val context: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PhotoRepository {

    companion object {
        const val TAG = "PhotoRepositoryImpl"
        val FIRST_TIME = booleanPreferencesKey("firstTime")
        val LOCAL_USER_VERSION = intPreferencesKey("userVersion")
    }

    /**
     * Data Store Preferences
     */
    override fun getFirstTime(): Flow<Boolean?> {
        return context.dataStore.data.map {
            Log.d(TAG, "$FIRST_TIME value -> ${it[FIRST_TIME]}")
            it[FIRST_TIME]
        }
    }

    override suspend fun checkFirstTime(): Boolean = (getFirstTime().first() == null)

    override suspend fun setFirstTime(isFirstTime: Boolean) {
        context.dataStore.edit {
            it[FIRST_TIME] = isFirstTime
            it[LOCAL_USER_VERSION] = Constants.LOCAL_USER_VERSION
            Log.d(TAG, "New $FIRST_TIME value -> ${it[FIRST_TIME]}")
            Log.d(TAG, "$LOCAL_USER_VERSION value -> ${it[LOCAL_USER_VERSION]}")
        }
    }

    override suspend fun updateLocalAppInfo(): Boolean {
        return try {
            val lastUpdate = photoDao.getLastAppInfoUpdateDate().first() ?: ""
            Log.d("updateLocalAppInfo()", "LastUpdate -> $lastUpdate")
            val updatedResponse = photoSource.getAppInfo(lastUpdate)
            if (updatedResponse.isNullOrEmpty()) return true
            updatedResponse.forEach { appInfo ->
                photoDao.insertAppInfo(appInfo)
                Log.d("updateLocalAppInfo", "Insert -> ${appInfo.id}")
            }
            true
        } catch (e: Exception) {
            Log.d("updateLocalAppInfo", e.toString())
            false
        }
    }

    /**
     * Photo data
     */
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
            val localLastInsertPhotoDate =
                LocalDateTime.parse(photoDao.getLastInsertPhotoDate().first())
            val remoteLastInsertPhotoDate = LocalDateTime.parse(lastRemoteUpdates.lastInsertDate)
            Log.d("updateLocalPhotoList", "Local -> $localLastInsertPhotoDate")
            Log.d("updateLocalPhotoList", "Remote -> $remoteLastInsertPhotoDate")
            if (remoteLastInsertPhotoDate.isAfter(localLastInsertPhotoDate)) {
                Log.d("updateLocalPhotoList", "False")
                if (insertNewPhotos(localLastInsertPhotoDate.toString())) {
                    Log.d("updateLocalPhotoList", "True")
                    updateLocalPhotoVotes(lastRemoteUpdates.lastVotesUpdate.toString())
                    true
                } else false
            } else {
                Log.d("updateLocalPhotoList", "True")
                updateLocalPhotoVotes(lastRemoteUpdates.lastVotesUpdate.toString())
                true
            }

        }
    }

    override suspend fun updateLocalPhotoVotes(remoteUpdate: String) {

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

    override suspend fun getWelcomeInfo(): List<InfoFromApi?>? {
        return photoDao.getAppInfo().first().welcome
    }

    override suspend fun getTutorialInfo(): List<InfoFromApi?>? {
        return photoDao.getAppInfo().first().tutorial
    }

    override suspend fun getAboutUsInfo(): List<InfoFromApi?>? {
        return photoDao.getAppInfo().first().aboutUs?.sortedBy { it?.priority }
    }

    override suspend fun getShareInfo(): List<InfoFromApi?>? {
        return photoDao.getAppInfo().first().share
    }

    override suspend fun getExtraInfo(): List<InfoFromApi?>? {
        return photoDao.getAppInfo().first().extra
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

    override suspend fun getTutorialVersusList(size: Int): List<Pair<Photo, Photo>> {
        val tutorialVersusPhoto = photoDao.getTutorialVersusPhoto().first().shuffled()
        Log.d("getTutorialVersusList", tutorialVersusPhoto.size.toString())
        return listOf(
            Pair(Photo("0"), Photo("0")),
            Pair(tutorialVersusPhoto[0], tutorialVersusPhoto[1]),
            Pair(Photo("0"), Photo("0"))
        )
    }

    override suspend fun getVersusList(ignorePair: Pair<String, String>): List<Pair<Photo, Photo>> {

        val versusPhotos = photoDao.getVersusPhoto().first()
            .filterNot {
                it.id == ignorePair.first || it.id == ignorePair.first
            }
            .sortedWith(compareBy(
                { it.localVersus }, { it.rank }
            ))

        val firstList = versusPhotos.subList(0, versusPhotos.size / 2).shuffled()
        val secondList = versusPhotos.subList(versusPhotos.size / 2, versusPhotos.size).shuffled()
        var randomPair = firstList.mapIndexed { index, localPhotoTitle ->
            if (index / 2 == 0) {
                Pair(localPhotoTitle, secondList[index])
            } else {
                Pair(secondList[index], localPhotoTitle)
            }
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
        return photoDao.getFavorites().first().map {
            photoDao.getLocalPhotoById(it.id).first()
        }.sortedByDescending { it.rank }
    }

    override suspend fun getPhotosByPlayer(playerId: String): List<Photo> {
        return photoSource.getPhotosByPlayer(playerId)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotosByMatch(matchId: String): List<Photo> {
        return photoSource.getPhotosByMatch(matchId)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotosByTag(tag: String): List<Photo> {
        return photoSource.getPhotosByTag(tag)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotosByPhotographer(photographerId: String): List<Photo> {
        return photoSource.getPhotosByPhotographer(photographerId)
            .toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getBestPhotos(): List<Photo> {
        return photoSource.getBestPhotos().toHiddenPhotoList(photoDao.getVotedPhotosIds().first())
    }

    override suspend fun getPhotoDetail(id: String): PhotoDto {
        val remotePhoto = photoSource.getPhotoById(id)
        updateLocalPhotoDetail(remotePhoto)
        return remotePhoto
    }

    override suspend fun getFavoritePairListState(pairIdList: List<Pair<String, String>>): List<Pair<Boolean, Boolean>> {
        return pairIdList.map {
            Pair(
                getFavoriteById(it.first),
                getFavoriteById(it.second)
            )
        }
    }

    override suspend fun getFavoriteListState(idList: List<String>): List<Boolean> {
        return idList.map { getFavoriteById(it) }
    }

    private suspend fun getFavoriteById(id: String): Boolean {
        val isFavorite = photoDao.getFavoriteById(id).first() != null
        Log.d("getFavorite", "$id $isFavorite")
        return isFavorite
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

    override suspend fun getTagDetail(tagId: String): Tag {
        return photoSource.getTag(tagId)
    }

    override suspend fun postVote(vote: Vote) {
        photoSource.postVote(vote.toVoteDto())
        photoDao.updateLocalPhoto(vote.photoWin.updateVoteWin(versusId = vote.photoLost.id))
        photoDao.updateLocalPhoto(vote.photoLost.updateVoteLost(versusId = vote.photoWin.id))
        updatePlayersLocalVotes(vote)
    }

    override suspend fun onlyLocalVote(vote: Vote) {
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
        if (getFavoriteById(photoId)) {
            photoDao.deleteFavorite(Favorites(photoId))
            Log.d("getFavorites", "delete $photoId")
        } else {
            Log.d("getFavorites", "insert $photoId")
            photoDao.insertFavorite(Favorites(photoId))
        }
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
            if (LocalDateTime.parse(remotePhoto.lastUpdate)
                    .isAfter(LocalDateTime.parse(localPhoto.lastUpdate))
            ) {
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