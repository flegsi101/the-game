package de.kugma.the_game.querBeet

import de.kugma.the_game.common.Countdown
import de.kugma.the_game.common.Team
import de.kugma.the_game.common.createPersistedState
import de.kugma.the_game.common.update
import kotlinx.coroutines.flow.onEach

private val TERMS: List<String> = listOf(
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

internal class KuensterDuettViewModel(game: QuerBeetGame) {

    private val _state =
        createPersistedState("querBeet_kuenstlerDuett.json", KuenstlerDuettState(TERMS.toList()))
    internal val state = _state.asStateFlow()

    private val countdown = Countdown(120) { remaining ->
        _state.update { it.copy(countdownSeconds = remaining) }
    }

    fun startCountdown() {
        countdown.start()
        _state.update { it.copy(countdownRunning = true) }
    }

    fun stopCountdown() {
        countdown.stop()
        _state.update { it.copy(countdownRunning = false) }
    }

    fun resetCountdown() {
        countdown.reset(120)
    }

    fun resetState() {
        _state.update { KuenstlerDuettState(TERMS.toList()) }
    }

    fun initTurn(team: Team) {
        val terms = mutableListOf<String>()
        val available = state.value.availableTerms.toMutableList()

        for (i in 0..4) {
            val term = available.random()
            terms.add(term)
            available.remove(term)
        }

        val turn = Turn(team = team, terms = terms)
        _state.update { it.copy(availableTerms = available, turn = turn) }
    }

    fun termCorrectGuessed(term: String) {
        when (state.value.turn!!.team) {
            Team.Team1 -> _state.update { it.copy(
                termsTeam1 = it.termsTeam1.plusElement(term),
            )}

            Team.Team2 -> _state.update { it.copy(
                termsTeam2 = it.termsTeam2.plusElement(term),
            )}

            else -> {}
        }
    }
}