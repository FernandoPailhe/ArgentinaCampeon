package com.ferpa.argentinacampeon.domain.model

data class Info(
    val id: String? = "",
    val title: String? = "",
    val content: String? = "",
    val link: String? = "",
    val iconUrl: String? = "",
    val priority: Int? = 0,
    val photoUrl: String? = "",
    val dateCondition: String? = ""
)
