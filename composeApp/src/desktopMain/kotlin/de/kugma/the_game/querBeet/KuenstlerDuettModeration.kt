package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                enabled = state.availableTerms.size >= 5 && state.turn?.team != Team.Team1
            ) { Text("Team 1") }
            Button(
                onClick = {
                    viewModel.initTurn(Team.Team2)
                    viewModel.resetCountdown()
                },
                enabled = state.availableTerms.size >= 5 && state.turn?.team != Team.Team2
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

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                for (term in state.turn!!.terms) {
                    Term(term, state, onCorrect = { viewModel.termCorrectGuessed(it) })
                }
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

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { onCorrect(term) }, enabled = !alreadyGuessed) { Text("correct") }

        if (state.countdownRunning)
            Text(
                term,
                fontSize = 60.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (!alreadyGuessed) Color.Black else GameColor.Blue,
                modifier = Modifier.padding(20.dp)
            )
        else
            Text(
                text = "**************",
                fontSize = 80.sp,
                fontWeight = FontWeight.SemiBold
            )
    }
}