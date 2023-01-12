package com.ferpa.argentinacampeon.domain.model

data class MatchTitle (
    val id: String = "",
    val date: String = "",
    val title: String? = "",
    val score: String? = ""
)

fun MatchTitle.twoLinesTitle() = if (this.title != null) this.title.replace(": ", ":" + System.getProperty("line.separator")) else ""