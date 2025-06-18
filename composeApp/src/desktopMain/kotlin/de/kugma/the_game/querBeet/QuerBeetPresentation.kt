package de.kugma.the_game.querBeet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.kugma.the_game.GameColor
import de.kugma.the_game.common.Team

@Composable
fun QuerBeetPresentation(game: QuerBeetGame, modifier: Modifier) {
    val state by game.state.collectAsState()

    when (state.currentRound) {
        QuerBeetRound.None -> Overview(game, modifier)
        QuerBeetRound.StaubsaugerStaffel -> StaubsaugerStaffelPresentation()
        QuerBeetRound.OhrenAufSpeed -> OhrenAufSpeed(game)
        QuerBeetRound.Schuetten -> SchuettenPresentation()
        QuerBeetRound.KuenstlerDuett -> KuenstlerDuettPresentation(game)
    }
}

@Composable
private fun Overview(querBeet: QuerBeetGame, modifier: Modifier) {
    val state by querBeet.state.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(0.5f)
    ) {
        SingleGame(QuerBeetRound.StaubsaugerStaffel, state)
        SingleGame(QuerBeetRound.KuenstlerDuett, state)
        SingleGame(QuerBeetRound.Schuetten, state)
        SingleGame(QuerBeetRound.OhrenAufSpeed, state)
    }
}

@Composable
private fun ColumnScope.SingleGame(round: QuerBeetRound, state: QuerBeetState) {
    val winner by derivedStateOf { state.winner[round] }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.height(IntrinsicSize.Max)
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(GameColor.Yellow.copy(if (winner == Team.Team1 || winner == Team.Both) 1f else 0.2f))
        ) {

        }
        Text(
            text = round.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(GameColor.Gray)
                .padding(horizontal = 40.dp, vertical = 20.dp)
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(GameColor.Red.copy(if (winner == Team.Team2 || winner == Team.Both) 1f else 0.2f))
        ) {

        }
    }
}

@Composable
private fun OhrenAufSpeed(querBeet: QuerBeetGame) {
    val state by querBeet.ohrenAufSpeedState.collectAsState(OhrenAufSpeedState())
    Text(QuerBeetRound.OhrenAufSpeed.title)

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