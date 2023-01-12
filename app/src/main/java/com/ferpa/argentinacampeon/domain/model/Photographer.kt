package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ferpa.argentinacampeon.common.Constants.UNKNOWN_AUTHOR

@Entity
data class Photographer(
    @PrimaryKey
    val id: String = "",
    val name: String? = "",
    val country: String? = "",
    val web: String? = "",
    val source: String? = "",
    val photos: List<String?>? = emptyList(),
    val links: List<String?>? = emptyList(),
    val lastUpdate: String? = ""
)

fun Photographer.getName() = this.name ?: UNKNOWN_AUTHOR