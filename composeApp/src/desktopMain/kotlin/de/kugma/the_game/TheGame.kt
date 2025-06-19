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
import de.kugma.the_game.composables.ScorePanel
import de.kugma.the_game.entites.Game
import de.kugma.the_game.entites.TheGameState
import de.kugma.the_game.jeopardy.Jeopardy
import de.kugma.the_game.jeopardy.JeopardyModeration
import de.kugma.the_game.jeopardy.JeopardyPresentation
import de.kugma.the_game.pubGames.PubGames
import de.kugma.the_game.pubGames.PubGamesModeration
import de.kugma.the_game.pubGames.PubGamesPresentation
import de.kugma.the_game.querBeet.QuerBeetGame
import de.kugma.the_game.querBeet.QuerBeetModeration
import de.kugma.the_game.querBeet.QuerBeetPresentation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

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
                if (state.currentGame != Game.None) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ScorePanel(
                            state.pointsTeamOne,
                            state.pointsTeamTwo,
                            title = state.currentGame.toString()
                        )
                        val fill = Modifier.weight(1f)

                        @Suppress("KotlinConstantConditions")
                        when (state.currentGame) {
                            Game.Jeopary -> JeopardyPresentation(jeopardy, fill)
                            Game.PubGames -> PubGamesPresentation(pubGames, fill)
                            Game.QuerBeet -> QuerBeetPresentation(querBeet, fill)
                            Game.None -> Text("Unreachable")
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
        _state.update { it.copy(currentGame = Game.None) }
    }

    private fun launchGame(game: Game) {
        GameLog.logToFile("launch $game")

        when (game) {
            Game.Jeopary -> jeopardy = Jeopardy()
            Game.PubGames -> pubGames = PubGames()
            Game.QuerBeet -> querBeet = QuerBeetGame(this)
            else -> {}
        }

        _state.update { it.copy(currentGame = game) }
    }

    private fun onGameWon(team: Team, game: Game) {
        _state.update {
            it.copy(
                currentGame = Game.None,
                pointsTeamOne = it.pointsTeamOne + (if (team == Team.Team1 || team == Team.Both) 1 else 0),
                pointsTeamTwo = it.pointsTeamTwo + (if (team == Team.Team2 || team == Team.Both) 1 else 0),
                jeopardyPlayed = it.jeopardyPlayed || game == Game.Jeopary,
                pubGamesPlayed = it.pubGamesPlayed || game == Game.PubGames,
                querBeetPlayed = it.querBeetPlayed || game == Game.QuerBeet
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
                        if (state.currentGame == Game.None) {
                            Text("Games")
                            Button(
                                onClick = { launchGame(Game.Jeopary) },
                                enabled = !state.jeopardyPlayed
                            ) { Text("Jeopardy") }
                            Button(
                                onClick = { launchGame(Game.PubGames) },
                                enabled = !state.pubGamesPlayed
                            ) { Text("Pub Games") }
                            Button(
                                onClick = { launchGame(Game.QuerBeet) },
                                enabled = !state.querBeetPlayed
                            ) { Text("Quer Beet") }
                        } else {
                            Text("Winner")
                            Button(
                                onClick = { onGameWon(Team.Team1, Game.Jeopary) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Yellow)
                            ) { Text("Team 1") }
                            Button(
                                onClick = { onGameWon(Team.Team2, Game.PubGames) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Red)
                            ) { Text("Team 2") }
                            Button(
                                onClick = { onGameWon(Team.Both, Game.QuerBeet) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = GameColor.Blue)
                            ) { Text("Both") }
                        }
                    }

                    when (state.currentGame) {
                        Game.Jeopary -> JeopardyModeration(jeopardy)
                        Game.PubGames -> PubGamesModeration(pubGames, modifier = Modifier)
                        Game.QuerBeet -> QuerBeetModeration(querBeet)
                        Game.None -> Button(onClick = { resetGame() }) {
                            Text("Reset Game")
                        }
                    }

                }

            }
        }
    }
}