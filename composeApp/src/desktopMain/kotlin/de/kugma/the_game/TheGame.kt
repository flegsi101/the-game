package de.kugma.the_game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import de.kugma.the_game.common.Storage
import de.kugma.the_game.common.Team
import de.kugma.the_game.jeopardy.Jeopardy
import de.kugma.the_game.jeopardy.JeopardyModeration
import de.kugma.the_game.jeopardy.JeopardyPresentation
import de.kugma.the_game.pubGames.PubGames
import de.kugma.the_game.pubGames.PubGamesModeration
import de.kugma.the_game.pubGames.PubGamesPresentation
import de.kugma.the_game.querBeet.QuerBeetGame
import de.kugma.the_game.querBeet.QuerBeetModeration
import de.kugma.the_game.querBeet.QuerBeetPresentation
import de.kugma.the_game.querBeet.QuerBeetRound
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

enum class GameWindow {
    Moderation, Presentation
}

enum class GameEnum {
    None,
    Jeopary,
    PubGames,
    QuerBeet
}

@Serializable
data class TheGameState(
    val currentGameEnum: GameEnum = GameEnum.None,
    val pointsTeamOne: Int = 0,
    val pointsTeamTwo: Int = 0,
    val jeopardyPlayed: Boolean = false,
    val pubGamesPlayed: Boolean = false,
    val querBeetPlayed: Boolean = false
)

@OptIn(DelicateCoroutinesApi::class)
class TheGame(
    private val onExit: () -> Unit
) {

    private var jeopardy = Jeopardy()
    private var pubGames = PubGames()
    private var querBeet: QuerBeetGame = QuerBeetGame(this)


    val stateFile = Storage.file("appState.json")

    val _state = MutableStateFlow<TheGameState>(TheGameState())
    val state = _state.asStateFlow()

    var loading by mutableStateOf(true)

    init {
        loadState()
        loading = false

        _state.onEach {
            val json = Json.encodeToString(it)
            stateFile.writeText(text = json, charset = Charsets.UTF_8)
        }.launchIn(GlobalScope)
    }

    private fun loadState() {
        val json = stateFile.readText(charset = Charsets.UTF_8)
        _state.update {
            if (json.isNotEmpty()) {
                Json.decodeFromString<TheGameState>(json)
            } else {
                TheGameState()
            }
        }
    }

    //======================================================
    // PRESENTATION
    //======================================================

    @Composable
    fun PresentationWindow() {
        val state by _state.collectAsState()

        Window(
            onCloseRequest = {},
            title = "The Game - Game",
            state = rememberWindowState(
                width = 1280.dp,
                height = 720.dp
            )
        ) {
            if (!loading) {
                if (state.currentGameEnum != GameEnum.None) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ScorePanel(
                            state.pointsTeamOne,
                            state.pointsTeamTwo,
                            title = state.currentGameEnum.toString()
                        )
                        val fill = Modifier.weight(1f)

                        @Suppress("KotlinConstantConditions")
                        when (state.currentGameEnum) {
                            GameEnum.Jeopary -> JeopardyPresentation(jeopardy, fill)
                            GameEnum.PubGames -> PubGamesPresentation(pubGames, fill)
                            GameEnum.QuerBeet -> querBeet.presentation(fill)
                            GameEnum.None -> Text("Unreachable")
                        }
                    }
                } else {
                    Column {
                        ScorePanel(
                            state.pointsTeamOne,
                            state.pointsTeamTwo,
                            title = "The Game"
                        )
                    }
                }
            }
        }
    }

    //======================================================
    // MODERATION
    //======================================================

    private fun home() {
        GameLog.logToFile("go home")
        _state.update { it.copy(currentGameEnum = GameEnum.None) }
    }

    private fun launchGame(gameEnum: GameEnum) {
        GameLog.logToFile("launch $gameEnum")

        when (gameEnum) {
            GameEnum.Jeopary -> jeopardy = Jeopardy()
            GameEnum.PubGames -> pubGames = PubGames()
            GameEnum.QuerBeet -> querBeet = QuerBeetGame(this)
            else -> {}
        }

        _state.update { it.copy(currentGameEnum = gameEnum) }
    }

    private fun onGameWon(team: Team, gameEnum: GameEnum) {
        _state.update {
            it.copy(
                currentGameEnum = GameEnum.None,
                pointsTeamOne = it.pointsTeamOne + (if (team == Team.Team1 || team == Team.Both) 1 else 0),
                pointsTeamTwo = it.pointsTeamTwo + (if (team == Team.Team2 || team == Team.Both) 1 else 0),
                jeopardyPlayed = it.jeopardyPlayed || gameEnum == GameEnum.Jeopary,
                pubGamesPlayed = it.pubGamesPlayed || gameEnum == GameEnum.PubGames,
                querBeetPlayed = it.querBeetPlayed || gameEnum == GameEnum.QuerBeet
            )
        }
    }

    private fun resetGame() {
        _state.update { TheGameState() }
    }

    @Composable
    fun ModerationWindow() {
        val state by _state.collectAsState()

        Window(onCloseRequest = onExit, title = "The Game - Moderator") {
            if (loading) {
                Text("loading...")
            } else {
                Column {


                    Row(modifier = Modifier.padding(10.dp)) {
                        Button(onClick = ::home) { Text("home") }
                        Box(modifier = Modifier.height(20.dp)) {}
                        if (state.currentGameEnum == GameEnum.None) {
                            Text("Games")
                            Button(
                                onClick = { launchGame(GameEnum.Jeopary) },
                                enabled = !state.jeopardyPlayed
                            ) { Text("Jeopardy") }
                            Button(
                                onClick = { launchGame(GameEnum.PubGames) },
                                enabled = !state.pubGamesPlayed
                            ) { Text("Pub Games") }
                            Button(
                                onClick = { launchGame(GameEnum.QuerBeet) },
                                enabled = !state.querBeetPlayed
                            ) { Text("Quer Beet") }
                        } else {
                            Text("Winner")
                            Button(
                                onClick = { onGameWon(Team.Team1, GameEnum.Jeopary) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Yellow)
                            ) { Text("Team 1") }
                            Button(
                                onClick = { onGameWon(Team.Team2, GameEnum.PubGames) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Red)
                            ) { Text("Team 2") }
                            Button(
                                onClick = { onGameWon(Team.Both, GameEnum.QuerBeet) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Blue)
                            ) { Text("Both") }
                        }
                    }

                    when (state.currentGameEnum) {
                        GameEnum.Jeopary -> JeopardyModeration(jeopardy)
                        GameEnum.PubGames -> PubGamesModeration(pubGames, modifier = Modifier)
                        GameEnum.QuerBeet -> querBeet.moderation()
                        GameEnum.None -> Button(onClick = { resetGame() }) {
                            Text("Reset Game")
                        }
                    }

                }

            }
        }
    }
}