package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Moment(
    @PrimaryKey
    val id: String = "",
    val date: String? = "",
    val gameTime: Int? = 0,
    val additionalTime: Int? = 0,
    val description: String? = "",
    val playType: String? = "",
    val lastUpdate: String? = ""
)
