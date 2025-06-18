package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import de.kugma.the_game.common.Team

@Composable
internal fun WinnerSelector(game: QuerBeetGame, round: QuerBeetRound) {
    Row {
        Text("Round winner:")
        Button(onClick = {game.onRoundWon(round, Team.Team1)}) {Text("Team 1")}
        Button(onClick = {game.onRoundWon(round, Team.Team2)}) {Text("Team 2")}
        Button(onClick = {game.onRoundWon(round, Team.Both)}) {Text("Both")}
    }
}
