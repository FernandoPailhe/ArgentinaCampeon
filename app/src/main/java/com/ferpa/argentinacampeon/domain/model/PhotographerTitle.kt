package com.ferpa.argentinacampeon.domain.model

data class PhotographerTitle(
    val id: String? = "",
    val name: String? = ""
)

fun PhotographerTitle.share(): String = if (!name.isNullOrEmpty()) "Foto de ${this.name}" else ""
