package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ferpa.argentinacampeon.common.Constants.UNKNOWN_AUTHOR

@Entity
data class Photographer(
    @PrimaryKey
    val id: String = "",
    val name: String? = null,
    val country: String? = null,
    val web: String? = null,
    val source: String? = null,
    val links: List<String?>? = null,
    val lastUpdate: String? = null
)

fun Photographer.getName() = this.name ?: UNKNOWN_AUTHOR

fun Photographer.getFirstLink(): String? = if (!this.links.isNullOrEmpty()) this.links.first() else null