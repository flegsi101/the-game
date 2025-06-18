package de.kugma.the_game.pubGames

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.kugma.the_game.GameColor
import de.kugma.the_game.common.Team

@Composable
fun PubGamesModeration(pubGames: PubGames, modifier: Modifier) {
    val state by pubGames.state.collectAsState()

    Column(
        modifier = modifier.padding(20.dp)
    ) {
        Row {
            Button(
                onClick = { pubGames.onGameSelect(Round.None) },
                modifier = Modifier.weight(1f)
            ) {
                Text("None")
            }
        }

        GameButton(pubGames, Round.Bierdeckflippen, "Bierdeckelflippen")

        GameButton(pubGames, Round.Wuerfeldart, "Würfeldart")
        if (state.currentGame == Round.Wuerfeldart)
            Wuerfeldart(pubGames)

        GameButton(pubGames, Round.Tresenschupsen, "Tresenschupsen")
        if (state.currentGame == Round.Tresenschupsen)
            Tresenschupse(pubGames)

        GameButton(pubGames, Round.Ausstechen, "Ausstechen")
        if (state.currentGame == Round.Ausstechen)
            Ausstechen(pubGames)
    }
}

@Composable
private fun ColumnScope.GameButton(pubGames: PubGames, game: Round, title: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Button(
            onClick = { pubGames.onGameSelect(game) },
            modifier = Modifier.weight(1f)
        ) {
            Text(title)
        }
        Button(
            onClick = { pubGames.setWinner(game, Team.None) },
            colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Gray)
        ) {
            Text("None")
        }
        Button(
            onClick = { pubGames.setWinner(game, Team.Team1) },
            colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Yellow)
        ) {
            Text("Team1")
        }
        Button(
            onClick = { pubGames.setWinner(game, Team.Team2) },
            colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Red)
        ) {
            Text("Team2")
        }
        Button(
            onClick = { pubGames.setWinner(game, Team.Both) },
            colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Blue)
        ) {
            Text("Both")
        }
    }
}

@Composable
private fun ColumnScope.Wuerfeldart(pubGames: PubGames) {
    val wuerfelPointsTeam1 = remember { TextFieldState() }
    val wuerfelPointsTeam2 = remember { TextFieldState() }

    Row {
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                pubGames.setWuerfeldartPoints(
                    Team.Team1,
                    wuerfelPointsTeam1.text.toString().toInt()
                )
            }) {
            Text("Commit")
        }
        TextField(
            modifier = Modifier.weight(1f),
            state = wuerfelPointsTeam1,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        TextField(
            modifier = Modifier.weight(1f),
            state = wuerfelPointsTeam2,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                pubGames.setWuerfeldartPoints(
                    Team.Team2,
                    wuerfelPointsTeam2.text.toString().toInt()
                )
            }) {
            Text("Commit")
        }
    }
}

@Composable
private fun ColumnScope.Tresenschupse(pubGames: PubGames) {
    val pointsTeam1 = remember { TextFieldState() }
    val pointsTeam2 = remember { TextFieldState() }

    Row(
        modifier = Modifier
            .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                pubGames.setTresenschupsenPoints(
                    Team.Team1,
                    pointsTeam1.text.toString().toInt()
                )
            }) {
            Text("Commit")
        }
        TextField(
            modifier = Modifier.weight(1f),
            state = pointsTeam1,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        TextField(
            modifier = Modifier.weight(1f),
            state = pointsTeam2,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                pubGames.setTresenschupsenPoints(
                    Team.Team2,
                    pointsTeam2.text.toString().toInt()
                )
            }) {
            Text("Commit")
        }
    }
}

@Composable
private fun ColumnScope.Ausstechen(pubGames: PubGames) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Runde 1", modifier = Modifier.padding(bottom = 5.dp))
            Button(onClick = { pubGames.setAusstechenWinner(0, Team.Team1) }) { Text("Team 1") }
            Button(onClick = { pubGames.setAusstechenWinner(0, Team.Team2) }) { Text("Team 2") }
            Button(onClick = { pubGames.setAusstechenWinner(0, Team.None) }) { Text("Clear") }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Runde 2", modifier = Modifier.padding(bottom = 5.dp))
            Button(onClick = { pubGames.setAusstechenWinner(1, Team.Team1) }) { Text("Team 1") }
            Button(onClick = { pubGames.setAusstechenWinner(1, Team.Team2) }) { Text("Team 2") }
            Button(onClick = { pubGames.setAusstechenWinner(1, Team.None) }) { Text("Clear") }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Runde 3", modifier = Modifier.padding(bottom = 5.dp))
            Button(onClick = { pubGames.setAusstechenWinner(2, Team.Team1) }) { Text("Team 1") }
            Button(onClick = { pubGames.setAusstechenWinner(2, Team.Team2) }) { Text("Team 2") }
            Button(onClick = { pubGames.setAusstechenWinner(2, Team.None) }) { Text("Clear") }

        }
    }
}