package de.kugma.the_game.querBeet

import de.kugma.the_game.common.Team
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
internal data class Turn(
    val terms: List<String>,
    val team: Team
)

@Serializable
internal data class KuenstlerDuettState(
    val availableTerms: List<String>,
    val termsTeam1: List<String> = listOf(),
    val termsTeam2: List<String> = listOf(),
    val turn: Turn? = null,
    @Transient val countdownSeconds: Int = 0,
    @Transient val countdownRunning: Boolean = false
)