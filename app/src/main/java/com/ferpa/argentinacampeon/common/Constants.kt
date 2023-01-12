package com.ferpa.argentinacampeon.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Constants {

    const val UNKNOWN_AUTHOR = "Unknown author"

    const val PARAM_PHOTO_ID = "photoId"
    const val PARAM_PLAYER_ID = "playerId"
    const val PARAM_MATCH_ID = "matchId"
    const val PARAM_TAG = "tag"
    const val PARAM_PHOTOGRAPHER_ID = "photographerId"

    /**
     * LISTS SIZE
     */
    const val BEST_PHOTOS_LIMIT = 20
    const val VERSUS_LIST_PAGE = 10

    /**
     * COLORS
     */
    val VioletUltraDark = Color(0xFF10101E)
    val VioletDark = Color(0xFF18182F)
    val Violet = Color(0xFF333366)
    val LightBlue = Color(0xFF75bae1)
    val LightBlueTransparent = Color(0x8E75BAE1)
    val VioletLight = Color(0xFF5c5388)
    val QatarDark = Color(0xFF58001D)

    /**
     * ANIMATION DURATIONS
     */
    const val POST_VOTE_DELAY: Long = 350
    const val NAV_ANIMATION_DURATION = 900

    /**
     * VIEWS SIZE
     */
    const val TOP_BACKGROUND_HEIGHT = 180

}
