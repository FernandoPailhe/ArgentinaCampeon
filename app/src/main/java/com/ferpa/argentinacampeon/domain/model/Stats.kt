package com.ferpa.argentinacampeon.domain.model

data class Stats(
    val matches: Int? = null,
    val minutes: Int? = null,
    val goals: Int? = null,
    val assists: Int? = null,
    val shots: Int? = null,
    val passes: Int? = null,
    val wrongPasses: Int? = null,
    val passAccuracy: Int? = null,
    val recoveries: Int? = null,
    val fouls: Int? = null,
    val foulsReceived: Int? = null,
    val yellowCards: Int? = null,
    val redCards: Int? = null
)
