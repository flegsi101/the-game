package de.kugma.the_game.querBeet

import de.kugma.the_game.common.Team
import kotlinx.serialization.Serializable

@Serializable
internal data class QuerBeetState(
    val currentRound: QuerBeetRound = QuerBeetRound.None,
    val ohrenAufSpeedState: OhrenAufSpeedState = OhrenAufSpeedState(),
    val winner: Map<QuerBeetRound, Team> = mapOf()
)