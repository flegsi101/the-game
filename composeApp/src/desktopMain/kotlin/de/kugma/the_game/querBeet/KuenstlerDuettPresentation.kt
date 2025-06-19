package de.kugma.the_game.querBeet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.kugma.the_game.GameColor
import de.kugma.the_game.common.Team

@Composable
internal fun KuenstlerDuettPresentation(game: QuerBeetGame) {
    val viewModel = game.kuenstlerDuettViewModel
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            Text(
                text = QuerBeetRound.KuenstlerDuett.title,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(100.dp),
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text("Team 1", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                    color = if(state.turn?.team == Team.Team1) Color.White else Color.Black,
                    modifier = Modifier
                    .background(
                        color = if(state.turn?.team == Team.Team1) GameColor.Blue else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                )
                Text(state.termsTeam1.size.toString(), fontSize = 30.sp)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text("Team 2", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                    color = if(state.turn?.team == Team.Team2) Color.White else Color.Black,
                    modifier = Modifier
                        .background(
                            color = if(state.turn?.team == Team.Team2) GameColor.Blue else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                )
                Text(state.termsTeam2.size.toString(), fontSize = 30.sp)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = state.countdownSeconds.toString(),
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = if (state.countdownSeconds == 0) Color.Black else Color.White,
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        color = if (state.countdownSeconds == 0) GameColor.Orange else GameColor.Blue,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

