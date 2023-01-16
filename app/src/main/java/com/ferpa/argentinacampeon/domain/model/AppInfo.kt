package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppInfo(
    @PrimaryKey
    val id: String = "",
    val welcome: List<InfoFromApi?>? = emptyList(),
    val tutorial: List<InfoFromApi?>? = emptyList(),
    val share: List<InfoFromApi?>? = emptyList(),
    val aboutUs: List<InfoFromApi?>? = emptyList(),
    val extra: List<InfoFromApi?>? = emptyList(),
    val lastUpdate: String? = ""
)
