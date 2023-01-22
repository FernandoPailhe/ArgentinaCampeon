package com.ferpa.argentinacampeon.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Match(
    @PrimaryKey
    val id: String,
    val date: String? = "",
    val stadiumId: String? = "",
    val tournamentInstance: String? = "",
    val teamA: String? = null,
    val teamB: String? = null,
    val referee: String? = "",
    val score: String? = "",
    val scoreTeamA: List<String?>? = emptyList(),
    val scoreTeamB: List<String?>? = emptyList(),
    val lastUpdate: String? = "",
    val description: String? = "",
    val extra: String? = "",
)

fun Match.getScore(): Pair<String, String> {
    val scoreItems = this.score?.split(" - ", " (")
    Log.d("getScore", scoreItems?.size.toString())
    return when (scoreItems?.size) {
        2 -> {
            Pair(scoreItems[0], scoreItems[1])
        }
        4 -> {
            Pair(
                scoreItems[0][0] + " (${scoreItems[2][0]})",
                scoreItems[1][0] + " (${scoreItems[3][0]})"
            )
        }
        else -> {
            Pair("", "")
        }
    }
}

fun Match.getTeamsAndScore(): Pair<String, String> {
    val scoreItems = this.score?.split(" - ", " (")
    Log.d("getScore", scoreItems?.size.toString())
    return when (scoreItems?.size) {
        2 -> {
            Pair(
                this.teamA + System.getProperty("line.separator") + scoreItems[0],
                this.teamB + System.getProperty("line.separator") + scoreItems[1]
            )
        }
        4 -> {
            Pair(
                this.teamA + System.getProperty("line.separator") + scoreItems[0][0] + System.getProperty(
                    "line.separator"
                ) + "(${scoreItems[2][0]})",
                this.teamB + System.getProperty("line.separator") + scoreItems[1][0] + System.getProperty(
                    "line.separator"
                ) + "(${scoreItems[3][0]})"
            )
        }
        else -> {
            Pair("", "")
        }
    }
}

fun Match.toMatchTitle(): MatchTitle {
    val title = if (this.teamA.isNullOrEmpty() || this.teamB.isNullOrEmpty()) {
        this.tournamentInstance
    } else "${this.tournamentInstance}: ${this.teamA} vs. ${this.teamB}"
    return MatchTitle(
        id = this.id,
        date = this.date ?: "",
        title = title,
        score = this.score
    )
}

fun Match.getMatchTitle(): String {
    return if (this.teamA.isNullOrEmpty() || this.teamB.isNullOrEmpty()) {
        this.tournamentInstance ?: ""
    } else {
        "${this.tournamentInstance}: ${this.teamA} vs. ${this.teamB}"
    }
}

fun Match.addUrl(url: String): Match = this.copy(extra = url)

fun Match.getItemTitle(): String {
    return if (this.teamA.isNullOrEmpty() || this.teamB.isNullOrEmpty()) {
        this.tournamentInstance ?: ""
    } else if (this.teamA == "Argentina") {
        this.teamB
    } else {
        this.teamA
    }
}