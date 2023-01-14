package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppInfo(
    @PrimaryKey
    val id: String = "",
    val welcome: List<Info?>? = emptyList(),
    val tutorial: List<Info?>? = emptyList(),
    val share: List<Info?>? = emptyList(),
    val aboutUs: List<Info?>? = emptyList(),
    val extra: List<Info?>? = emptyList(),
    val lastUpdate: String? = ""
)
