package de.kugma.the_game.entites

import kotlinx.serialization.Serializable

@Serializable
data class TheGameState (
    val currentGame: Game = Game.None,
    val pointsTeamOne: Int = 0,
    val pointsTeamTwo: Int = 0,
    val jeopardyPlayed: Boolean = false,
    val pubGamesPlayed: Boolean = false,
    val querBeetPlayed: Boolean = false
)