package de.kugma.the_game.jeopardy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.kugma.the_game.GameColor
import de.kugma.the_game.GameWindow

@Composable
fun JeopardyModeration(game: Jeopardy) {
    val onTitle by game.onTitle.collectAsState()
    val state by game.state.collectAsState()
    val openQuestion by game.currentQuestion.collectAsState(null)

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Jeopardy - Moderation",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (onTitle) {
                Button(onClick = { game.start() }) { Text("Start") }
            } else {


                Button(
                    onClick = { game.reset() }
                ) {
                    Text("Reset")
                }

                if (openQuestion != null) {
                    Button(
                        onClick = { game.closeQuestion(false) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Gray)
                    ) {
                        Text("Back")
                    }

                    Button(
                        onClick = {
                            game.closeQuestion(true)
                            game.givePointsTeamOne(openQuestion!!.getPoints())
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Yellow)
                    ) {
                        Text("Team 1")
                    }
                    Button(
                        onClick = {
                            game.closeQuestion(true)
                            game.givePointsTeamTwo(openQuestion!!.getPoints())
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Red)
                    ) {
                        Text("Team 2")
                    }
                    Button(
                        onClick = { game.closeQuestion(true) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Blue)
                    ) {
                        Text("No Team")
                    }
                }
            }
        }

        if (!onTitle) {
            if (openQuestion != null) {
                openQuestion!!.render(GameWindow.Moderation)
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuestionCategory(game, 0)
                    QuestionCategory(game, 1)
                    QuestionCategory(game, 2)
                    QuestionCategory(game, 3)
                    QuestionCategory(game, 4)
                }
            }
        }
    }
}

@Composable
private fun RowScope.QuestionCategory(game: Jeopardy, category: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
    ) {
        Text(
            text = Jeopardy.Categories[category],
            modifier = Modifier.height(40.dp)
        )
        QuestionTrigger(game, category, 0, "100")
        QuestionTrigger(game, category, 1, "200")
        QuestionTrigger(game, category, 2, "300")
        QuestionTrigger(game, category, 3, "400")
        QuestionTrigger(game, category, 4, "500")
    }
}

@Composable
private fun QuestionTrigger(game: Jeopardy, category: Int, points: Int, text: String) {
    val state by game.state.collectAsState()
    Button(
        onClick = { game.openQuestion(category, points) },
        enabled = !state.questionStates[category][points]
    ) {
        Text(text)
    }
}