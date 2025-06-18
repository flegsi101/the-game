package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.kugma.the_game.common.Team

@Composable
internal fun KuenstlerDuettPresentation(game: QuerBeetGame) {
    val viewModel = game.kuenstlerDuettViewModel
    val state by viewModel.state.collectAsState()

    Column {
        Row {
            Text("Punkte Team 1: ${state.termsTeam1.size}")
            Text("Punkte Team 2: ${state.termsTeam2.size}")
        }

        if (state.turn != null) {
            Column {
                Text(
                    when (state.turn!!.team) {
                        Team.Team1 -> "Team 1"
                        Team.Team2 -> "Team 2"
                        else -> ""
                    }
                )
                Text(state.countdownSeconds.toString())
            }
        }
    }
}

