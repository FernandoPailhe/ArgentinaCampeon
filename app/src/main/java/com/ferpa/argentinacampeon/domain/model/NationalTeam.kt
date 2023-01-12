package com.ferpa.argentinacampeon.domain.model

data class NationalTeam(
    val id: String?,
    val name: String? = "",
    val nickName: String? = "",
    val logoUrl: String? = "",
    val flagUrl: String? = ""
)
