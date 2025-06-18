package de.kugma.the_game.jeopardy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import de.kugma.the_game.GameWindow

@Composable
fun JeopardyPresentation(
    game: Jeopardy,
    modifier: Modifier = Modifier
) {
    val onTitle by game.onTitle.collectAsState()
    val state by game.state.collectAsState()
    val openQuestion by game.currentQuestion.collectAsState(null)

    if (!onTitle) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .padding(20.dp),
        ) {
            if (openQuestion != null) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (state.countdownRunning) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = state.countdownSeconds.toString(),
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (state.countdownSeconds > 0) Color.White else Color.Black,
                                modifier = Modifier
                                    .width(100.dp)
                                    .align(Alignment.Center)
                                    .background(
                                        color = if (state.countdownSeconds > 0) GameColor.Blue else GameColor.Orange,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(vertical = 5.dp)
                            )
                        }
                    }
                    openQuestion!!.render(GameWindow.Presentation)
                }
            } else {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuestionCatergory(state, 0)
                    QuestionCatergory(state, 1)
                    QuestionCatergory(state, 2)
                    QuestionCatergory(state, 3)
                    QuestionCatergory(state, 4)
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Team 1 - ${state.pointsTeamOne}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${state.pointsTeamOne} - Team 2",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun RowScope.QuestionCatergory(state: State, index: Int) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
    ) {
        Text(
            text = Jeopardy.Categories[index],
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xff3a3a3a))
                .padding(10.dp)
        )
        QuestionCard("100", state.questionStates[index][0])
        QuestionCard("200", state.questionStates[index][1])
        QuestionCard("300", state.questionStates[index][2])
        QuestionCard("400", state.questionStates[index][3])
        QuestionCard("500", state.questionStates[index][4])
    }
}

@Composable
private fun ColumnScope.QuestionCard(text: String, marked: Boolean) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .weight(1f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (marked) GameColor.Gray else GameColor.Blue)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}