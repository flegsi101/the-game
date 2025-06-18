package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import de.kugma.the_game.GameColor
import de.kugma.the_game.common.Team

@Composable
internal fun KuenstlerDuettModeration(game: QuerBeetGame) {
    val viewModel = game.kuenstlerDuettViewModel
    val state by viewModel.state.collectAsState()

    Column {
        WinnerSelector(game, QuerBeetRound.KuenstlerDuett)

        Row {
            Text("available terms: ${state.availableTerms.size}")
            Button(
                onClick = {
                    viewModel.initTurn(Team.Team1)
                    viewModel.resetCountdown()
                },
                enabled = state.availableTerms.size >= 5
            ) { Text("Team 1") }
            Button(
                onClick = {
                    viewModel.initTurn(Team.Team2)
                    viewModel.resetCountdown()
                },
                enabled = state.availableTerms.size >= 5
            ) { Text("Team 2") }
            Button(onClick = { viewModel.resetState() }) { Text("reset") }
        }

        if (state.turn != null) {
            Text(state.turn!!.team.name)
            Text(state.countdownSeconds.toString())

            if (!state.countdownRunning) {
                Button(onClick = {
                    viewModel.startCountdown()
                }) {
                    Text("Start")
                }
            } else {
                Button(onClick = {
                    viewModel.stopCountdown()
                }) {
                    Text("Stop")
                }
            }

            for (term in state.turn!!.terms) {
                Term(term, state, onCorrect = { viewModel.termCorrectGuessed(it) })
            }
        }
    }
}

@Composable
private fun Term(term: String, state: KuenstlerDuettState, onCorrect: (term: String) -> Unit) {
    val alreadyGuessed by derivedStateOf {
        when (state.turn!!.team) {
            Team.Team1 -> state.termsTeam1.contains(term)
            Team.Team2 -> state.termsTeam2.contains(term)
            else -> false
        }
    }

    Row {
        Box {
            if (state.countdownRunning)
                Text(term, color = if (!alreadyGuessed) Color.Black else GameColor.Blue)
            else
                Text("***")
        }
        Button(onClick = { onCorrect(term) }, enabled = !alreadyGuessed) { Text("correct") }
    }
}