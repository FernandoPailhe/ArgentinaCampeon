package com.ferpa.argentinacampeon.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ferpa.argentinacampeon.data.utils.Converters
import com.ferpa.argentinacampeon.domain.model.*

@Database(
    version = 1,
    entities = [
        Photo::class,
        Player::class,
        Match::class,
        Moment::class,
        Photographer::class,
        Favorites::class,
        AppInfo::class
    ],
    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class PhotoRoomDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "argentina_campeon_db"
    }

    abstract fun photoDao(): PhotoDao
}