package de.kugma.the_game.pubGames

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
fun PubGamesPresentation(game: PubGames, modifier: Modifier) {
    val state by game.state.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(0.5f)
    ) {
        SingleGame(
            "Bierdeckelflippen",
            state.currentGame == Round.Bierdeckflippen,
            state.team[Round.Bierdeckflippen] ?: Team.None
        )

        SingleGame(
            "Würfeldart",
            state.currentGame == Round.Wuerfeldart,
            state.team[Round.Wuerfeldart] ?: Team.None
        )
        if (state.currentGame == Round.Wuerfeldart) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                Text(state.wuerfeldartPoints[Team.Team1].toString())
                Text(state.wuerfeldartPoints[Team.Team2].toString())
            }
        }

        SingleGame(
            "Tresenschupse",
            state.currentGame == Round.Tresenschupsen,
            state.team[Round.Tresenschupsen] ?: Team.None
        )
        if (state.currentGame == Round.Tresenschupsen) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                Text(state.tresenschupsenPoints[Team.Team1].toString())
                Text(state.tresenschupsenPoints[Team.Team2].toString())
            }
        }

        SingleGame(
            "Ausstechen",
            state.currentGame == Round.Ausstechen,
            state.team[Round.Ausstechen] ?: Team.None
        )
        if (state.currentGame == Round.Ausstechen) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                AusstechenPoint(game, 0)
                AusstechenPoint(game, 1)
                AusstechenPoint(game, 2)
            }
        }
    }
}

@Composable
private fun ColumnScope.SingleGame(title: String, isActive: Boolean, team: Team) {
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
                .background(GameColor.Yellow.copy(if (team == Team.Team1 || team == Team.Both) 1f else 0.2f))
        ) {

        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(if (isActive) GameColor.Blue else GameColor.Gray)
                .padding(horizontal = 40.dp, vertical = 20.dp)
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(GameColor.Red.copy(if (team == Team.Team2 || team == Team.Both) 1f else 0.2f))
        ) {

        }
    }
}

@Composable
fun AusstechenPoint(pubGames: PubGames, round: Int) {
    val state by pubGames.state.collectAsState()

    Box(
        modifier = Modifier
            .size(40.dp)
            .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = when (state.ausstechenWinner[round]) {
                    Team.Team1 -> Color.Yellow
                    Team.Team2 -> Color.Red
                    else -> Color.Transparent
                }
            )
    ) {}
}