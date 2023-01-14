package com.ferpa.argentinacampeon.data.remote

import com.ferpa.argentinacampeon.common.Constants.BEST_PHOTOS_LIMIT
import com.ferpa.argentinacampeon.common.ServerRoutes.MATCH_BASE_ROUTE
import com.ferpa.argentinacampeon.common.ServerRoutes.PHOTOGRAPHER_BASE_ROUTE
import com.ferpa.argentinacampeon.common.ServerRoutes.PHOTO_BASE_ROUTE
import com.ferpa.argentinacampeon.common.ServerRoutes.PLAYER_BASE_ROUTE
import com.ferpa.argentinacampeon.common.ServerRoutes.TAGS_BASE_ROUTE
import com.ferpa.argentinacampeon.data.remote.dto.*
import com.ferpa.argentinacampeon.domain.model.*
import com.ferpa.argentinacampeon.domain.model.Tag
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ArgentinaCampeonService {

    @GET("{downloadPath}")
    suspend fun downloadImage(downloadPath: String): Response<ResponseBody>

    /**
     * UPDATE STATUS
     */
    @GET("${PHOTO_BASE_ROUTE}/lastUpdatesDates")
    suspend fun getLastUpdatesDates(): LastUpdatesResponse

    /**
     * PHOTO API CALLS
     */
    @GET(PHOTO_BASE_ROUTE)
    suspend fun getNewPhotos(@Query("getFrom") updateFrom: String): List<PhotoDto>

    @GET("$PHOTO_BASE_ROUTE/rankUpdates")
    suspend fun getVotesUpdate(@Query("getFrom") updateFrom: String): List<RankUpdateDto>

    @GET("${PHOTO_BASE_ROUTE}/best")
    suspend fun getBestPhotos(@Query("limit") limit: Int = BEST_PHOTOS_LIMIT): List<PhotoDto>

    @GET("${PHOTO_BASE_ROUTE}/id/{id}")
    suspend fun getPhotoById(@Path("id") id: String): PhotoDto

    @GET("${PHOTO_BASE_ROUTE}${PLAYER_BASE_ROUTE}/{playerId}")
    suspend fun getPhotosByPlayer(@Path("playerId") playerId: String): List<PhotoDto>

    @GET("${PHOTO_BASE_ROUTE}${MATCH_BASE_ROUTE}/{matchId}")
    suspend fun getPhotosByMatch(@Path("matchId") matchId: String): List<PhotoDto>

    @GET("${PHOTO_BASE_ROUTE}$TAGS_BASE_ROUTE/{tag}")
    suspend fun getPhotosByTag(@Path("tag") tag: String): List<PhotoDto>

    @GET("${PHOTO_BASE_ROUTE}${PHOTOGRAPHER_BASE_ROUTE}/{photographerId}")
    suspend fun getPhotosByPhotographer(@Path("photographerId") photographerId: String): List<PhotoDto>

    @GET("${PLAYER_BASE_ROUTE}/{id}")
    suspend fun getPlayer(@Path("id") id: String): Player

    @GET("${MATCH_BASE_ROUTE}/{id}")
    suspend fun getMatch(@Path("id") id: String): Match

    @GET("${PHOTOGRAPHER_BASE_ROUTE}/{id}")
    suspend fun getPhotographer(@Path("id") id: String): Photographer

    @GET("${TAGS_BASE_ROUTE}/{id}")
    suspend fun getTag(@Path("id") id: String): Tag

    @POST("${PHOTO_BASE_ROUTE}/vote")
    suspend fun postVote(@Body voteDto: VoteDto)

    /**
     * PLAYER API CALLS
     */
    @GET(PLAYER_BASE_ROUTE)
    suspend fun getPlayersList(@Query("getFrom") getFrom: String): List<PlayerDto>

    @POST("$PLAYER_BASE_ROUTE/new")
    suspend fun postPlayer(@Body playerDto: PlayerDto): Response<Any>

    /**
     * MATCH API CALLS
     */
    @GET(MATCH_BASE_ROUTE)
    suspend fun getMatchList(@Query("getFrom") getFrom: String): List<Match>

}
