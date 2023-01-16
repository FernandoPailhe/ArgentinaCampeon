package com.ferpa.argentinacampeon.presentation


import com.ferpa.argentinacampeon.common.Constants.PARAM_MATCH_ID
import com.ferpa.argentinacampeon.common.Constants.PARAM_PHOTOGRAPHER_ID
import com.ferpa.argentinacampeon.common.Constants.PARAM_PHOTO_ID
import com.ferpa.argentinacampeon.common.Constants.PARAM_PLAYER_ID
import com.ferpa.argentinacampeon.common.Constants.PARAM_TAG

sealed class Screen(val route: String) {
    object BestPhotosScreenRoute: Screen("best_photos_screen")
    object FavoritePhotosScreenRoute: Screen("favorite_photos_screen")
    object StoryScreenRoute: Screen("story_screen")
    object AboutUsScreenRoute: Screen("info_screen")
    object InsertDataScreenRoute: Screen("insert_data_screen")
    object PhotoListScreenRoute: Screen("photo_list_screen")
    object PhotoListByPlayerScreenRoute: Screen("photo_list_screen_by_player/{${PARAM_PLAYER_ID}}") {
        fun createRoute(playerId: String) = "photo_list_screen_by_player/$playerId"
    }
    object PhotoListByMatchScreenRoute: Screen("photo_list_screen_by_match/{${PARAM_MATCH_ID}}") {
        fun createRoute(matchId: String) = "photo_list_screen_by_match/$matchId"
    }
    object PhotoListByPhotographerScreenRoute: Screen("photo_list_screen_by_photographer/{${PARAM_PHOTOGRAPHER_ID}}") {
        fun createRoute(photographerId: String) = "photo_list_screen_by_photographer/$photographerId"
    }
    object PhotoListByTagScreenRoute: Screen("photo_list_screen_by_tag/{${PARAM_TAG}}") {
        fun createRoute(tag: String) = "photo_list_screen_by_tag/$tag"
    }
    object PhotoDetailScreenRoute: Screen("photo_detail_screen/{${PARAM_PHOTO_ID}}") {
        fun createRoute(photoId: String) = "photo_detail_screen/$photoId"
    }
    object VersusScreenRoute: Screen("versus_screen")
}