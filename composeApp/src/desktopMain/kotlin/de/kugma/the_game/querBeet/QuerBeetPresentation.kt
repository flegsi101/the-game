package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun QuerBeetPresentation(game: QuerBeet, modifier: Modifier) {
    val state by game.querBeetState.collectAsState()

    when (state.currentRound) {
        Round.None -> Overview(game)
        Round.StaubsaugerStaffel -> StaubsaugerStaffel(game)
        Round.OhrenAufSpeed -> OhrenAufSpeed(game)
        Round.Schuetten -> Schuetten(game)
        Round.KuenstlerDuett -> KuenstlerDuettPresentation(game)
    }
}

@Composable
private fun Overview(querBeet: QuerBeet) {

}

@Composable
private fun StaubsaugerStaffel(querBeet: QuerBeet) {
    Text(Round.StaubsaugerStaffel.title)
}

@Composable
private fun OhrenAufSpeed(querBeet: QuerBeet) {
    val state by querBeet.ohrenAufSpeedState.collectAsState(OhrenAufSpeedState())
    Text(Round.OhrenAufSpeed.title)

    Row {
        Column {
            Text("Team 1")
            Text(state.pointsTeam1.toString())
        }

        Column {
            Text("Team 2")
            Text(state.pointsTeam2.toString())
        }
    }
}

@Composable
private fun Schuetten(querBeet: QuerBeet) {
    Text(Round.Schuetten.title)
}