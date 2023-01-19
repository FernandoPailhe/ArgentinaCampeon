package com.ferpa.argentinacampeon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppInfo(
    @PrimaryKey
    val id: String = "",
    val welcome: List<ServerInfo?>? = emptyList(),
    val tutorial: List<ServerInfo?>? = emptyList(),
    val share: List<ServerInfo?>? = emptyList(),
    val aboutUs: List<ServerInfo?>? = emptyList(),
    val extra: List<ServerInfo?>? = emptyList(),
    val lastUpdate: String? = ""
)
