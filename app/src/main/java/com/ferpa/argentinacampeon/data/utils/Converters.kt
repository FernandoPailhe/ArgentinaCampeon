package com.ferpa.argentinacampeon.data.utils

import androidx.room.TypeConverter
import com.ferpa.argentinacampeon.domain.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {

    @TypeConverter
    fun stringToListPlayer(data: String?): List<Player?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Player?>?>() {}.type
        return Gson().fromJson<List<Player?>>(data, listType)
    }

    @TypeConverter
    fun listPlayerToString(someObjects: List<Player?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListPlayerTitle(data: String?): List<PlayerTitle?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<PlayerTitle?>?>() {}.type
        return Gson().fromJson<List<PlayerTitle?>>(data, listType)
    }

    @TypeConverter
    fun listPlayerTitleToString(someObjects: List<PlayerTitle?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListMatch(data: String?): List<Match?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Match?>?>() {}.type
        return Gson().fromJson<List<Match?>>(data, listType)
    }

    @TypeConverter
    fun listMatchToString(someObjects: List<Match?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToMatch(data: String?): Match? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<Match?>() {}.type
        return Gson().fromJson<Match?>(data, objectType)
    }

    @TypeConverter
    fun matchToString(someObjects: Match?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToStats(data: String?): Stats? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<Stats?>() {}.type
        return Gson().fromJson<Stats?>(data, objectType)
    }

    @TypeConverter
    fun statsToString(someObjects: Stats?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToMatchTitle(data: String?): MatchTitle? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<MatchTitle?>() {}.type
        return Gson().fromJson<MatchTitle?>(data, objectType)
    }

    @TypeConverter
    fun matchTitleToString(someObjects: MatchTitle?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListTag(data: String?): List<Tag?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Tag?>?>() {}.type
        return Gson().fromJson<List<Tag?>>(data, listType)
    }

    @TypeConverter
    fun listTagToString(someObjects: List<Tag?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListGoal(data: String?): List<Goal?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Goal?>?>() {}.type
        return Gson().fromJson<List<Goal?>>(data, listType)
    }

    @TypeConverter
    fun listGoalToString(someObjects: List<Goal?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListString(data: String?): List<String?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<String?>?>() {}.type
        return Gson().fromJson<List<String?>>(data, listType)
    }

    @TypeConverter
    fun listStringToString(someObjects: List<String?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToPhotographer(data: String?): Photographer? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<Photographer?>() {}.type
        return Gson().fromJson<Photographer?>(data, objectType)
    }

    @TypeConverter
    fun photographerToString(someObjects: Photographer?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToPhotographerTitle(data: String?): PhotographerTitle? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<PhotographerTitle?>() {}.type
        return Gson().fromJson<PhotographerTitle?>(data, objectType)
    }

    @TypeConverter
    fun photographerTitleToString(someObjects: PhotographerTitle?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToMomentTitle(data: String?): MomentTitle? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<MomentTitle?>() {}.type
        return Gson().fromJson<MomentTitle?>(data, objectType)
    }

    @TypeConverter
    fun momentTitleToString(someObjects: MomentTitle?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToNationalTeam(data: String?): NationalTeam? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<NationalTeam?>() {}.type
        return Gson().fromJson<NationalTeam?>(data, objectType)
    }

    @TypeConverter
    fun nationalTeamToString(someObjects: NationalTeam?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToPhotoType(data: String?): PhotoType? {
        if (data == null) {
            return null
        }
        val objectType: Type = object :
            TypeToken<PhotoType?>() {}.type
        return Gson().fromJson<PhotoType?>(data, objectType)
    }

    @TypeConverter
    fun photoTypeToString(someObjects: PhotoType?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListInfo(data: String?): List<Info?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Info?>?>() {}.type
        return Gson().fromJson<List<Info?>>(data, listType)
    }

    @TypeConverter
    fun listInfoTitleToString(someObjects: List<Info?>?): String? {
        return Gson().toJson(someObjects)
    }

}