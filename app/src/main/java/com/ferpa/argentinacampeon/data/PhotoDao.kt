package com.ferpa.argentinacampeon.data

import androidx.room.*
import com.ferpa.argentinacampeon.domain.model.Photo
import com.ferpa.argentinacampeon.domain.model.Match
import com.ferpa.argentinacampeon.domain.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    /**
     * PHOTO TABLE
     */

    @Insert
    suspend fun insertLocalPhoto(photo: Photo)

    @Update
    suspend fun updateLocalPhoto(photo: Photo)

    @Query("SELECT * from photo")
    fun getAllLocalPhoto(): Flow<List<Photo>>

    @Query("SELECT * from photo ORDER BY localVersus ASC LIMIT 20")
    fun getVersusPhoto(): Flow<List<Photo>>

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
    @Query("SELECT * from player")
    fun getPlayers(): Flow<List<Player>>

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

    @Query("SELECT lastUpdate from `match` ORDER BY lastUpdate DESC LIMIT 1")
    fun getLastMatchUpdateDate(): Flow<String>

    @Query("SELECT * from `match` WHERE id = :id")
    fun getMatchById(id: String): Flow<Match>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match)

}