package com.ferpa.argentinacampeon.common

import androidx.compose.ui.graphics.Color

object Constants {

    const val UNKNOWN_AUTHOR = "Unknown author"

    const val PARAM_PHOTO_ID = "photoId"
    const val PARAM_PLAYER_ID = "playerId"
    const val PARAM_MATCH_ID = "matchId"
    const val PARAM_TAG = "tag"
    const val PARAM_PHOTOGRAPHER_ID = "photographerId"


    const val NO_TAG_KEY = "no_tag"


    /**
     * DATA STORE
     */
    const val DATA_STORE_NAME = "localUser"
    const val LOCAL_USER_VERSION = 1

    /**
     * LISTS SIZE
     */
    const val BEST_PHOTOS_LIMIT = 20
    const val VERSUS_LIST_PAGE = 10

    /**
     * COLORS
     */
    val VioletRadialGradient = Color(0x8018182F)
    val VioletUltraDark = Color(0xFF10101E)
    val VioletDark = Color(0xFF18182F)
    val Violet = Color(0xFF333366)
    val VioletTransparent = Color(0xCA333366)
    val LightBlue = Color(0xFF75bae1)
    val LightBlueTransparent = Color(0x8E75BAE1)
    val VioletLight = Color(0xFF5c5388)
    val QatarDark = Color(0xFF58001D)

    /**
     * ANIMATION DURATIONS
     */
    const val LOAD_SCREEN_DELAY: Long = 20000
    const val POST_VOTE_DELAY: Long = 350
    const val NAV_ANIMATION_DURATION = 900

    /**
     * VIEWS SIZE
     */
    const val TOP_BACKGROUND_HEIGHT = 180
    const val CARD_PHOTO_LIST_ITEM_HEIGHT = 140
    const val ICON_SIZE = (CARD_PHOTO_LIST_ITEM_HEIGHT / 5)
    const val WELCOME_DIALOG_OFFSET = 200

}
