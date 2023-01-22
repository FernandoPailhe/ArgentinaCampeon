package com.ferpa.argentinacampeon.data.local

import androidx.room.*
import com.ferpa.argentinacampeon.domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    /**
     * APP INFO TABLE
     */
    @Query("SELECT * FROM appInfo LIMIT 1")
    fun getAppInfo(): Flow<AppInfo>

    @Query("SELECT lastUpdate FROM appInfo ORDER BY lastUpdate DESC LIMIT 1")
    fun getLastAppInfoUpdateDate(): Flow<String>

    @Query("SELECT * from appInfo WHERE id = :id")
    fun getAppInfoById(id: String): Flow<AppInfo>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(appInfo: AppInfo)

    @Update
    suspend fun updateAppInfo(appInfo: AppInfo)

    /**
     * PHOTO TABLE
     */
    @Insert
    suspend fun insertLocalPhoto(photo: Photo)

    @Update
    suspend fun updateLocalPhoto(photo: Photo)

    @Query("SELECT * from photo")
    fun getAllLocalPhoto(): Flow<List<Photo>>

    @Query("SELECT * from photo WHERE rarity > -1 ORDER BY localVersus ASC LIMIT 30")
    fun getVersusPhoto(): Flow<List<Photo>>

    @Query("SELECT * from photo WHERE rarity > -1 AND localVersus = 0 ORDER BY rank DESC LIMIT 60")
    fun getVersusPhotoNoVoted(): Flow<List<Photo>>

    @Query("SELECT * from photo WHERE insertDate = 1")
    fun getTutorialVersusPhoto(): Flow<List<Photo>>

    @Query("SELECT id from photo WHERE localVersus > 0")
    fun getVotedPhotosIds(): Flow<List<String>>

    @Query("SELECT insertDate from photo ORDER BY insertDate DESC LIMIT 1")
    fun getLastInsertPhotoDate(): Flow<String>

    @Query("SELECT insertDate from photo ORDER BY votesUpdate DESC LIMIT 1")
    fun getLastVotesUpdate(): Flow<String>

    @Query("SELECT * from photo WHERE id = :id")
    fun getLocalPhotoById(id: String): Flow<Photo>

    /**
     * PLAYER TABLE
     */
    @Query("SELECT * from player WHERE photoCount > 0 ORDER BY photoCount DESC")
    fun getPlayers(): Flow<List<Player>>

    @Query("SELECT * from player ORDER BY photoCount DESC")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * from player WHERE photoCount = -1 AND profilePhotoUrl IS NULL")
    fun getPlayersWithoutPhotoData(): Flow<List<Player>>

    @Query("SELECT * from player  WHERE (displayName LIKE :search OR name LIKE :search OR nickName LIKE :search)  ORDER BY photoCount DESC")
    fun getPlayersWithSearch(search: String): Flow<List<Player>>

    @Query("SELECT lastUpdate from player ORDER BY lastUpdate DESC LIMIT 1")
    fun getLastPlayerUpdateDate(): Flow<String>

    @Query("SELECT * from player WHERE id = :id")
    fun getPlayerById(id: String): Flow<Player>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player)

    @Update
    suspend fun updateLocalPlayer(player: Player)

    /**
     * MATCH TABLE
     */
    @Query("SELECT * from `match`")
    fun getMatches(): Flow<List<Match>>

    @Query("SELECT * from `match` WHERE (tournamentInstance LIKE :search OR teamA LIKE :search OR teamB LIKE :search)")
    fun getMatchesWithSearch(search: String): Flow<List<Match>>

    @Query("SELECT lastUpdate from `match` ORDER BY lastUpdate DESC LIMIT 1")
    fun getLastMatchUpdateDate(): Flow<String>

    @Query("SELECT * from `match` WHERE id = :id")
    fun getMatchById(id: String): Flow<Match>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match)

    /**
     * FAVORITES TABLE
     */
    @Query("SELECT * from favorites WHERE id = :id")
    fun getFavoriteById(id: String): Flow<Favorites?>

    @Query("SELECT * from favorites")
    fun getFavorites(): Flow<List<Favorites>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorites)

    @Delete
    suspend fun deleteFavorite(favorite: Favorites)
}