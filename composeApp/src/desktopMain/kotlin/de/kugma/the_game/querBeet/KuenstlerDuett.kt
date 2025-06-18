package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.kugma.the_game.common.Team
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
internal data class Term(
    val text: String,
    val used: Boolean = false
)

internal val terms: List<String> = listOf(
    "Flammenwerfer",
    "Schlüssel",
    "Ballett",
    "Gift",
    "Gießkanne",
    "Billiard",
    "Frosch",
    "Rasenmäher",
    "Trampolin",
    "Wecker",
    "Eishockey",
    "Tischtennis",
    "Drache",
    "Schere",
    "Maske",
    "Pizza",
    "Schwimmflossen",
    "Brücke",
    "Leuchtturm",
    "Windmühle",
    "Teufel",
    "Schnorchel",
    "Kopfhörer",
    "Gitarre",
    "Katze",
    "Clown",
    "Dusche",
    "Vampir",
    "Flugzeug",
    "Schwert",
)

@Serializable
internal data class KuenstlerDuettState(
    val termsTeam1Round1: List<Term> = listOf(),
    val termsTeam2Round1: List<Term> = listOf(),
    val termsTeam1Round2: List<Term> = listOf(),
    val termsTeam2Round2: List<Term> = listOf(),
    val termsTeam1Round3: List<Term> = listOf(),
    val termsTeam2Round3: List<Term> = listOf(),
    val initialized: Boolean = false,
    val currentTerm: Term? = null,
    val currentRound: Int = 0,
    val currentTeam: Team = Team.Team1
)

internal fun initKuenstlerDuettRounds(state: KuenstlerDuettState): KuenstlerDuettState {
    val availableTerms = terms.toMutableList()

    val team1Round1 = mutableListOf<Term>()
    for (i in 0..4) {
        val term = availableTerms[Random.nextInt(0, availableTerms.size)]
        team1Round1.add(Term(text = term))
        availableTerms.remove(term)
    }

    val team2Round1 = mutableListOf<Term>()
    for (i in 0..4) {
        val term = availableTerms[Random.nextInt(0, availableTerms.size)]
        team2Round1.add(Term(text = term))
        availableTerms.remove(term)
    }

    val team1Round2 = mutableListOf<Term>()
    for (i in 0..4) {
        val term = availableTerms[Random.nextInt(0, availableTerms.size)]
        team1Round2.add(Term(text = term))
        availableTerms.remove(term)
    }

    val team2Round2 = mutableListOf<Term>()
    for (i in 0..4) {
        val term = availableTerms[Random.nextInt(0, availableTerms.size)]
        team2Round2.add(Term(text = term))
        availableTerms.remove(term)
    }

    val team1Round3 = mutableListOf<Term>()
    for (i in 0..4) {
        val term = availableTerms[Random.nextInt(0, availableTerms.size)]
        team1Round3.add(Term(text = term))
        availableTerms.remove(term)
    }

    val team2Round3 = mutableListOf<Term>()
    for (i in 0..4) {
        val term = availableTerms[Random.nextInt(0, availableTerms.size)]
        team2Round3.add(Term(text = term))
        availableTerms.remove(term)
    }

    return state.copy(
        termsTeam1Round1 = team1Round1,
        termsTeam2Round1 = team2Round1,
        termsTeam1Round2 = team1Round2,
        termsTeam2Round2 = team2Round2,
        termsTeam1Round3 = team1Round3,
        termsTeam2Round3 = team2Round3,
        initialized = true
    )
}

@Composable
internal fun KuenstlerDuettPresentation(game: QuerBeet) {
    val state by game.kuenstlerDuettState.collectAsState(KuenstlerDuettState())

    if (state.currentTerm != null) {

    }
}

@Composable
internal fun KuenstlerDuettModeration(game: QuerBeet) {
    val state by game.kuenstlerDuettState.collectAsState(KuenstlerDuettState())

    var team1Rights by remember { mutableStateOf(0) }
    var team2Rights by remember { mutableStateOf(0) }
    var roundStarted by remember { mutableStateOf(false) }
    var termIndex by remember { mutableStateOf(0) }

    fun updateTerm() {
        game.updateKuenstlerDuett {
            it.copy(
                currentTerm = when (Pair(it.currentRound, it.currentTeam)) {
                    Pair(0, Team.Team1) -> it.termsTeam1Round1[termIndex]
                    Pair(0, Team.Team2) -> it.termsTeam2Round1[termIndex]
                    Pair(1, Team.Team1) -> it.termsTeam1Round2[termIndex]
                    Pair(1, Team.Team2) -> it.termsTeam2Round2[termIndex]
                    Pair(2, Team.Team1) -> it.termsTeam1Round3[termIndex]
                    Pair(2, Team.Team2) -> it.termsTeam2Round3[termIndex]
                    else -> null
                }
            )
        }
    }

    fun nextTerm() {
        termIndex += 1
        if (termIndex < 5)
            updateTerm()
        else {

        }
    }

    fun onRight() {
        when (state.currentTeam) {
            Team.Team1 -> team1Rights += 1
            Team.Team2 -> team2Rights += 1
            else -> {}
        }
        nextTerm()
    }

    if (!state.initialized) {
        Button(onClick = { game.initKuenstlerDuett() }) {
            Text("init")
        }
        return
    }

    Button(onClick = { game.resetKuenstlerDuett() }) { Text("reset") }

    if (!roundStarted) {
        Text(text = "Next Team: ${state.currentTeam}")
        Button(onClick = {
            updateTerm()
            roundStarted = true
        }) { Text("start") }
    } else {
        Row {
            Text(text = state.currentTeam.name)
            Text(text = "${termIndex + 1} / 5")
        }
        Text(text = state.currentTerm?.text ?: "null")
        Row {
            Button(onClick = ::onRight) { Text("right") }
            Button(onClick = ::nextTerm) { Text("wrong") }
        }
    }
}